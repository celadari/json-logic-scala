// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


/**
 * Companion object holding implicit writer json.
 */
object SimpleTypeValue {

  implicit val writeSimpleTypeValue: Writes[SimpleTypeValue] = new Writes[SimpleTypeValue] {
    override def writes(o: SimpleTypeValue): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}

/**
 * Represents a simple type (Int, String, ...), user defined classes and types which are not Option, Array, Map.
 * @param codename: unique codename to identify type data in json-logic-typed format.
 */
case class SimpleTypeValue(override val codename: String) extends TypeValue(codename)
