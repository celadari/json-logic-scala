package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultShort extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(s) => {
        s match {
          case short: Short => short
          case jShort: java.lang.Short => jShort
          case _ => throw new IllegalInputException(s"Operator OptionToShort can only be applied to Option[Short] or Short. Input condition: $value")
        }
      }
      case None => 0.toShort
      case short: Short => short
      case jShort: java.lang.Short => jShort
      case _ => throw new IllegalInputException(s"Operator OptionToShort can only be applied to Option[Short] or Short. Input condition: $value")
    }
  }
}
