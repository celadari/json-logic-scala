// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


/**
 * Companion object that holds implicit writer json and constant codename for [[com.celadari.jsonlogicscala.tree.types.OptionTypeValue]].
 */
object OptionTypeValue {
  val CODENAME_TYPE: String = "option"

  implicit val writeOptionTypeValue: Writes[OptionTypeValue] = new Writes[OptionTypeValue] {
    override def writes(o: OptionTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

/**
 * Represents an Option[paramType].
 * @param paramType: parameter type this option is of.
 * @note inherited operator has fixed value of "option".
 */
case class OptionTypeValue(paramType: TypeValue) extends TypeValue(OptionTypeValue.CODENAME_TYPE)
