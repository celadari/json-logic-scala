// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of Boolean AND.
 */
object OperatorAnd extends Operator {

  def $amp$amp(bool1: java.lang.Boolean, bool2: java.lang.Boolean): java.lang.Boolean = bool1 && bool2
}
