package com.celadari.jsonlogicscala.evaluate.impl

import com.celadari.jsonlogicscala.evaluate.Operator


object OperatorImplGreater extends Operator {

  override def toString: String = this.getClass.getName
  def greater(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Boolean = num1 > num2
}
