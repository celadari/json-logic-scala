package com.github.celadari.jsonlogicscala.refs

import play.api.libs.json._

object RefValueLogic {

  implicit def refValueLogicReads[T](implicit fmt: Reads[T]): Reads[RefValueLogic[T]] = new Reads[RefValueLogic[T]] {
    override def reads(json: JsValue): JsResult[RefValueLogic[T]] = {
      val `var` = (json \ "var").as[T]
      val `type` = (json \ "").as[String]
      JsSuccess(RefValueLogic(`var`, `type`))
    }
  }

  implicit def refValueLogicWrites[T](implicit fmt: Writes[T]): Writes[RefValueLogic[T]] = new Writes[RefValueLogic[T]] {
    override def writes(refValueLogic: RefValueLogic[T]): JsValue = {
      JsObject(Map(
        "var" -> Json.toJson(refValueLogic.`var`),
        "type" -> Json.toJson(refValueLogic.`type`)
      ))
    }
  }
}

case class RefValueLogic[T](`var`: T, `type`: String) extends RefJsonLogicCore[T]
