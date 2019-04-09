package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object ComposeLogic {

  val OPERATORS = Array("<=", "<", ">=", ">", "==", "!=", "or", "and", "xor")

  private[core] def parse[T](jsonLogic: JsObject, jsonLogicData: JsObject)(implicit fmt: Reads[T]): ComposeLogic[T] = {
    // check for operator field
    val fields = jsonLogic.fields

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
    val operator = fields.head._1
    if (!OPERATORS.contains(operator)) throw new Error(s"Invalid parsed operator: $operator")

    // if operator is compose logic
    ComposeLogic[T](operator, readArrayOfConditions[T]((jsonLogic \ operator).get, jsonLogicData)(fmt))
  }

  private[core] def readArrayOfConditions[T](json: JsValue, jsonLogicData: JsObject)(implicit fmt: Reads[T]): Array[JsonLogicCore[_]] = {
    val jsArray = json.asInstanceOf[JsArray]
    jsArray
      .value
      .map(jsValue => {
        JsonLogicCore.parse[T](jsValue.asInstanceOf[JsObject], jsonLogicData)(fmt)
      })
      .toArray
  }

  implicit def composeLogicReads[T](implicit fmt: Reads[T]): Reads[ComposeLogic[T]] = new Reads[ComposeLogic[T]] {

    override def reads(json: JsValue): JsResult[ComposeLogic[T]] = {
      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = (json \ 1).getOrElse(JsObject.empty).asInstanceOf[JsObject]

      // apply reading with distinguished components: logic and data
      JsSuccess(parse[T](jsonLogic, jsonLogicData)(fmt))
    }
  }

}

case class ComposeLogic[T](operator: String, conditions: Array[JsonLogicCore[_]]) extends JsonLogicCore[T](operator)