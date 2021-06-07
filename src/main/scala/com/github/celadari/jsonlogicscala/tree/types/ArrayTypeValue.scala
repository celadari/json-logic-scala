package com.github.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}

object ArrayTypeValue {
  val CODENAME_TYPE: String = "array"

  implicit val writeArrayTypeValue: Writes[ArrayTypeValue] = new Writes[ArrayTypeValue] {
    override def writes(o: ArrayTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

case class ArrayTypeValue(paramType: TypeValue) extends TypeValue(ArrayTypeValue.CODENAME_TYPE)
