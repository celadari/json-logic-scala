// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsValue, Writes}


/**
 * Represents an unknown type. Evaluating, serializing, deserializing a tree with a leaf node with
 * [[com.celadari.jsonlogicscala.tree.types.AnyTypeValue]] as typeOpt parameters throws an exception.
 * Can only be used to manipulate a [[com.celadari.jsonlogicscala.tree.ValueLogic]] as temporary scala data structure
 * before setting correct typeOpt parameter.
 */
object AnyTypeValue extends TypeValue("anyTypeValue") {
  val CODENAME_TYPE: String = codename

  implicit val writeArrayTypeValue: Writes[AnyTypeValue.type] = new Writes[AnyTypeValue.type] {
    override def writes(o: AnyTypeValue.type): JsValue = TypeValue.writesTypeValue.writes(o)
  }
}
