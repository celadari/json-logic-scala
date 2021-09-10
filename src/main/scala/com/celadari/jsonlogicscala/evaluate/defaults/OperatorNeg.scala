package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorNeg extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case bool: Boolean => !bool
      case bool: java.lang.Boolean => !bool
      case other => throw new IllegalInputException(s"Operator Neg can only be applied to boolean values. Input condition: ${other.toString}")
    }
  }
}
