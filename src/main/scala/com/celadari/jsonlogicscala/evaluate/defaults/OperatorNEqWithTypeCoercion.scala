// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of NON-EQUAL.
 */
object OperatorNEqWithTypeCoercion extends Operator {

  def $bang$eq(value1: java.lang.Object, value2: java.lang.Object): java.lang.Boolean = value1 != value2
}
