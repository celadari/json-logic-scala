package com.github.celadari.jsonlogicscala.evaluate.impl

import com.github.celadari.jsonlogicscala.evaluate.Operator

class OperatorImplPrefix(val prefix: String) extends Operator {

  override def equals(that: Any): Boolean = {
    that match {
      case operator: OperatorImplPrefix => prefix == operator.prefix
      case _ => false
    }
  }

  override def hashCode(): Int = prefix.hashCode

  def prefix(values: Array[java.lang.Object]): java.lang.String = {
    s"$prefix${values(0)}"
  }
}
