// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


/**
 * Companion object holding implicit writer json and constant codename for [[com.celadari.jsonlogicscala.tree.types.MapTypeValue]].
 */
object MapTypeValue {
  val CODENAME_TYPE: String = "map"

  implicit val writeMapTypeValue: Writes[MapTypeValue] = new Writes[MapTypeValue] {
    override def writes(o: MapTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

/**
 * Represents a Map[String, paramType] type.
 * @param paramType: parameter type this map is of.
 * @note inherited operator has fixed value of "map".
 */
case class MapTypeValue(paramType: TypeValue) extends TypeValue(MapTypeValue.CODENAME_TYPE)
