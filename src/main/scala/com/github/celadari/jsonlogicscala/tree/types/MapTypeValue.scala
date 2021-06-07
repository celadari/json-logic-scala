package com.github.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}

object MapTypeValue {
  val CODENAME_TYPE: String = "map"

  implicit val writeMapTypeValue: Writes[MapTypeValue] = new Writes[MapTypeValue] {
    override def writes(o: MapTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

case class MapTypeValue(paramType: TypeValue) extends TypeValue(MapTypeValue.CODENAME_TYPE)
