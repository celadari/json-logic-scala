package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorEqStrict extends Operator {

  def $eq$eq$eq(value1: java.lang.Object, value2: java.lang.Object): java.lang.Boolean = value1.equals(value2)
}