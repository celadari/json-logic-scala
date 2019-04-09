package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object ValueLogic {
  implicit def valueLogicReads[T](implicit fmt: Reads[T]): Reads[ValueLogic[T]] = new Reads[ValueLogic[T]] {
    override def reads(json: JsValue): JsResult[ValueLogic[T]] = {
      JsSuccess(ValueLogic("var", (json \ "var").as[T]))
    }
  }
}

case class ValueLogic[T](operator: String, value: T) extends JsonLogicCore[T](operator)