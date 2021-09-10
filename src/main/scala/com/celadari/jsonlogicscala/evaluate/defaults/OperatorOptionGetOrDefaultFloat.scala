package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultFloat extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(f) => {
        f match {
          case _: Float => f
          case _: java.lang.Float => f
          case _ => throw new IllegalInputException(s"Operator OptionToFloat can only be applied to Option[Float] or Float. Input condition: $value")
        }
      }
      case None => 0f
      case _: Float => value
      case _: java.lang.Float => value
      case _ => throw new IllegalInputException(s"Operator OptionToFloat can only be applied to Option[Float] or Float. Input condition: $value")
    }
  }
}
