// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.impl

import com.celadari.jsonlogicscala.evaluate.Operator


class OperatorImplSurroundString(val prefix: String, val suffix: String) extends Operator {

  override def equals(that: Any): Boolean = {
    that match {
      case operator: OperatorImplSurroundString => (prefix == operator.prefix) && (suffix == operator.suffix)
      case _ => false
    }
  }

  override def hashCode(): Int = {
    prefix.hashCode + suffix.hashCode
  }

  def surroundString(value: Any): Any = {
    s"$prefix${value.asInstanceOf[String]}$suffix"
  }
}
