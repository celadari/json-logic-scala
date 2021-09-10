package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultInt extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(i) => {
        i match {
          case _: Int => i
          case _: java.lang.Integer => i
          case _ => throw new IllegalInputException(s"Operator OptionToInt can only be applied to Option[Int] or Int. Input condition: $value")
        }
      }
      case None => 0
      case _: Int => value
      case _: java.lang.Integer => value
      case _ => throw new IllegalInputException(s"Operator OptionToInt can only be applied to Option[Int] or Int. Input condition: $value")
    }
  }
}
