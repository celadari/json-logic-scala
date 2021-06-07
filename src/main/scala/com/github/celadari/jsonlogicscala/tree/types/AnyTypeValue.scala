package com.github.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


object AnyTypeValue extends TypeValue("anyTypeValue") {
  val CODENAME_TYPE: String = codename

  implicit val writeArrayTypeValue: Writes[AnyTypeValue.type] = new Writes[AnyTypeValue.type] {
    override def writes(o: AnyTypeValue.type): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}
