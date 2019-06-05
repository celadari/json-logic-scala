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

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): JsonLogicCore = {
    // check for operator field
    val fields = jsonLogic.fields

    // if operator is data access
    if (fields.map(_._1).contains("var")) return ValueLogic.decode(jsonLogic, jsonLogicData)(decoder)

    // check for compose logic operator field
    if (fields.isEmpty) return empty
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")

    // if operator is compose logic
    ComposeLogic.decode(jsonLogic, jsonLogicData)(decoder)
  }

  implicit def jsonLogicCoreReads(implicit decoder: Decoder): Reads[JsonLogicCore] = new Reads[JsonLogicCore] {

    override def reads(json: JsValue): JsResult[JsonLogicCore] = {
      // check if empty
      if (json == JsObject.empty) return JsSuccess(empty)

      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = (json \ 1).getOrElse(JsObject.empty).asInstanceOf[JsObject]

      // apply reading with distinguished components: logic and data
      JsSuccess(decode(jsonLogic, jsonLogicData)(decoder))
    }
  }

  implicit def jsonLogicCoreWrites(implicit encoder: Encoder): Writes[JsonLogicCore] = new Writes[JsonLogicCore] {
    override def writes(jsonLogicCore: JsonLogicCore): JsValue = {
      // check if empty
      if (jsonLogicCore.isEmpty) return JsObject.empty

      // apply writing
      val (jsonLogic, jsonLogicData) = encode(jsonLogicCore)(encoder)
      JsArray(Array(jsonLogic, jsonLogicData))
    }
  }

  def empty: JsonLogicCore = ComposeLogic("", Array.empty[JsonLogicCore])
}


abstract class JsonLogicCore(val operator: String) {

  def reduce(implicit reducer: ReduceLogic): Any = {
    reducer.reduce(this)
  }

  def isEmpty: Boolean
}