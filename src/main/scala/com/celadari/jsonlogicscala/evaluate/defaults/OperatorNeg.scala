// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorNeg extends UnaryOperator {

  /**
   * Returns negation value of boolean.
   * @param value: value operator operates on.
   * @return negated value.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is not a Boolean type.
   */
  def unaryOperator(value: Any): Any = {
    value match {
      case bool: Boolean => !bool
      case bool: java.lang.Boolean => !bool
      case other => throw new IllegalInputException(s"Operator Neg can only be applied to boolean values. Input condition: ${other.toString}")
    }
  }
}
