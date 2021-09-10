package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultString extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(s) => {
        s match {
          case string: String => string
          case _ => throw new IllegalInputException(s"Operator OptionToString can only be applied to Option[String] or String. Input condition: $value")
        }
      }
      case None => ""
      case string: String => string
      case _ => throw new IllegalInputException(s"Operator OptionToString can only be applied to Option[String] or String. Input condition: $value")
    }
  }
}
