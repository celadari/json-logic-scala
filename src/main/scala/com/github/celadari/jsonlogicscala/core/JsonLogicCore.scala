package com.github.celadari.jsonlogicscala.core

import com.github.celadari.jsonlogicscala.operators.ReduceLogic
import play.api.libs.json._

object JsonLogicCore {

  private[core] def encode(jsonLogic: JsonLogicCore)(implicit encoder: Encoder): (JsValue, JsObject) = {
    // if operator is data access
    jsonLogic match {
      case valueLogic: ValueLogic[_] => ValueLogic.encode(valueLogic)(encoder)
      case composeLogic: ComposeLogic => ComposeLogic.encode(composeLogic)(encoder)
    }
  }

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject, isDataArray: Boolean = false)
                          (implicit decoder: Decoder): JsonLogicCore = {
    // check for operator field
    val fields = jsonLogic.fields

    // if operator is data access
    if (fields.map(_._1).contains("var")) {
      val valueLogic = ValueLogic.decode(jsonLogic, jsonLogicData, isDataArray)(decoder)
      valueLogic.isDataArray = isDataArray
      return valueLogic
    }

    // check for compose logic operator field
    if (fields.isEmpty) return empty
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")

    // if operator is compose logic
    val composeLogic = ComposeLogic.decode(jsonLogic, jsonLogicData, isDataArray)(decoder)
    composeLogic.isDataArray = isDataArray
    composeLogic
  }

  implicit def jsonLogicCoreReads(implicit decoder: Decoder): Reads[JsonLogicCore] = new Reads[JsonLogicCore] {

    override def reads(json: JsValue): JsResult[JsonLogicCore] = {
      // check if empty
      if (json == JsObject.empty) return JsSuccess(empty)

      // check is jsonLogic is set to boolean value
      if ((json \ 0).validate[JsBoolean].isSuccess) return JsSuccess(ValueLogic("VALUE", (json \ 0).asOpt[Boolean], "boolean"))

      // check if jsonLogicData is an array
      val isDataArray = if ((json \ 1).validate[JsArray].isSuccess) true
        else if ((json \ 1).validate[JsObject].isSuccess) false
        else throw new IllegalArgumentException(s"Wrong data format for json-logic-data: ${json \ 1}")

      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = if (!isDataArray) (json \ 1).as[JsObject]
        else JsObject((json \ 1).as[JsArray].value.zipWithIndex.map{case (js, idx) => (idx.toString, js)}.toMap)

      // apply reading with distinguished components: logic and data
      val jsonLogicCore = decode(jsonLogic, jsonLogicData, isDataArray)(decoder)

      // return final result
      JsSuccess(jsonLogicCore)
    }
  }

  implicit def jsonLogicCoreWrites(implicit encoder: Encoder): Writes[JsonLogicCore] = new Writes[JsonLogicCore] {

    override def writes(jsonLogicCore: JsonLogicCore): JsValue = {
      // check if empty
      if (jsonLogicCore.isEmpty) return JsObject.empty

      // apply writing
      val (jsonLogic, jsonLogicDataObj) = encode(jsonLogicCore)(encoder)

      // write jsonLogicData to array if isDataArray
      val jsonLogicData = if (!jsonLogicCore.isDataArray) jsonLogicDataObj
        else JsArray(jsonLogicDataObj
          .fields
          .map{case (idxStr, js) => (idxStr.toInt, js)}
          .sortBy(_._1)
          .map(_._2))

      // return final result
      JsArray(Array(jsonLogic, jsonLogicData))
    }
  }

  def empty: JsonLogicCore = ValueLogic("", None, "")
}


abstract class JsonLogicCore(val operator: String) {

  /**
    * Indicates if parsed Json logic Data was in Array format when parsed.
    * Two distinct formats are accepted as data: object or array.
    * When parsing, array is transformed into object with array-index (toString) as object-key.
    * @note play.lib.json does not accept objects with keys as integers.
    */
  private[this] var _isDataArray: Boolean = false
  def isDataArray: Boolean = _isDataArray
  def isDataArray_= (newValue: Boolean): this.type = {
    _isDataArray = newValue
    this
  }

  def reduce(implicit reducer: ReduceLogic): Any = {
    reducer.reduce(this)
  }

  def isEmpty: Boolean
}