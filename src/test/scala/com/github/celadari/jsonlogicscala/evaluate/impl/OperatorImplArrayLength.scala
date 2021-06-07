package com.github.celadari.jsonlogicscala.evaluate.impl

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorImplArrayLength extends Operator {

  override def toString: String = this.getClass.getName
  def length(value: Any): java.lang.Integer = value.asInstanceOf[Array[_]].length
}
