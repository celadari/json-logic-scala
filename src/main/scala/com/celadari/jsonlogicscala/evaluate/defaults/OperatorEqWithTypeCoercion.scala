// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of EQUAL.
 * Type coercion means that two objects with different types can be equal.
 */
object OperatorEqWithTypeCoercion extends Operator {

  def $eq$eq(value1: java.lang.Object, value2: java.lang.Object): java.lang.Boolean = value1 == value2
}
