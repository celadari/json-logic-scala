package com.github.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}

object SimpleTypeValue {

  implicit val writeSimpleTypeValue: Writes[SimpleTypeValue] = new Writes[SimpleTypeValue] {
    override def writes(o: SimpleTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

case class SimpleTypeValue(override val codename: String) extends TypeValue(codename)
