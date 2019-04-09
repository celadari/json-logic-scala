package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object JsonLogicCore {

  private[core] def parse[T](jsonLogic: JsObject, jsonLogicData: JsObject)(implicit fmt: Reads[T]): JsonLogicCore[T] = {
    // check for operator field
    val fields = jsonLogic.fields

    // if operator is data access
    if (fields.map(_._1).contains("var")) return ValueLogic.parse[T](jsonLogic, jsonLogicData)(fmt)

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
    val operator = fields.head._1
    if (!ComposeLogic.OPERATORS.contains(operator)) throw new Error(s"Invalid parsed operator: $operator")

    // if operator is compose logic
    ComposeLogic.parse[T](jsonLogic, jsonLogicData)(fmt)
  }

  implicit def jsonLogicCoreReads[T](implicit fmt: Reads[T]): Reads[JsonLogicCore[T]] = new Reads[JsonLogicCore[T]] {

    override def reads(json: JsValue): JsResult[JsonLogicCore[T]] = {

      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = (json \ 1).getOrElse(JsObject.empty).asInstanceOf[JsObject]

      // apply reading with distinguished components: logic and data
      JsSuccess(parse(jsonLogic, jsonLogicData))
    }
  }
}


abstract class JsonLogicCore[T](operator: String)