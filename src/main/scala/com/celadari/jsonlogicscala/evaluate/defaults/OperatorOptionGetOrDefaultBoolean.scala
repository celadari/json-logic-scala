package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultBoolean extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(bool) => {
        bool match {
          case _: Boolean => bool
          case _: java.lang.Boolean => bool
          case _ => throw new IllegalInputException(s"Operator OptionToBoolean can only be applied to Option[Boolean] or Boolean. Input condition: $value")
        }
      }
      case None => false
      case _: Boolean => value
      case _: java.lang.Boolean => value
      case _ => throw new IllegalInputException(s"Operator OptionToBoolean can only be applied to Option[Boolean] or Boolean. Input condition: $value")
    }
  }
}
