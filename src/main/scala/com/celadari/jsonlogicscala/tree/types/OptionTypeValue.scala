package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


object OptionTypeValue {
  val CODENAME_TYPE: String = "option"

  implicit val writeOptionTypeValue: Writes[OptionTypeValue] = new Writes[OptionTypeValue] {
    override def writes(o: OptionTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

case class OptionTypeValue(paramType: TypeValue) extends TypeValue(OptionTypeValue.CODENAME_TYPE)
