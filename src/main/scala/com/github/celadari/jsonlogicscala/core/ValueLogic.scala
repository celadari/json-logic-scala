package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object ValueLogic {

  private[core] def parse[T](jsonLogic: JsObject, jsonLogicData: JsObject)(implicit fmt: Reads[T]): ValueLogic[T] = {
    val pathData = (jsonLogic \ "var").as[String]
    ValueLogic("var", (jsonLogicData \ pathData).as[T])
  }

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): ValueLogic[_] = {
    val typeData = (jsonLogic \ "type").as[String]
    val pathData = (jsonLogic \ "var").as[String]
    val json = (jsonLogicData \ pathData).get
    ValueLogic("var", decoder.decode(json, typeData))
  }

  implicit def valueLogicReads[T](implicit fmt: Reads[T]): Reads[ValueLogic[T]] = new Reads[ValueLogic[T]] {
    override def reads(json: JsValue): JsResult[ValueLogic[T]] = {
      JsSuccess(ValueLogic("var", (json \ "var").as[T]))
    }
  }
}

case class ValueLogic[T](operator: String, value: T) extends JsonLogicCore(operator)