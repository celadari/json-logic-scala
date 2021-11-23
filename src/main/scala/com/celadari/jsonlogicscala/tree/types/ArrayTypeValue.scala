// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


/**
 * Companion object that holds implicit writer json and constant codename for [[com.celadari.jsonlogicscala.tree.types.ArrayTypeValue]].
 */
object ArrayTypeValue {
  val CODENAME_TYPE: String = "array"

  implicit val writeArrayTypeValue: Writes[ArrayTypeValue] = new Writes[ArrayTypeValue] {
    override def writes(o: ArrayTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

/**
 * Represents a Array[paramType] type.
 * @param paramType: parameter type this array is of.
 * @note inherited operator has fixed value of "array".
 */
case class ArrayTypeValue(paramType: TypeValue) extends TypeValue(ArrayTypeValue.CODENAME_TYPE)
