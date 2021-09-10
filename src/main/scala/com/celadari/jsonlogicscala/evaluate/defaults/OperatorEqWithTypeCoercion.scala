package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


object OperatorEqWithTypeCoercion extends Operator {

  def $eq$eq(value1: java.lang.Object, value2: java.lang.Object): java.lang.Boolean = value1 == value2
}
