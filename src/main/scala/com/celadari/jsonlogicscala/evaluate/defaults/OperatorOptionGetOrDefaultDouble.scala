package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultDouble extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(d) => {
        d match {
          case _: Double => d
          case _: java.lang.Double => d
          case _ => throw new IllegalInputException(s"Operator OptionToDouble can only be applied to Option[Double] or Double. Input condition: $value")
        }
      }
      case None => 0d
      case _: Double => value
      case _: java.lang.Double => value
      case _ => throw new IllegalInputException(s"Operator OptionToDouble can only be applied to Option[Double] or Double. Input condition: $value")
    }
  }
}
