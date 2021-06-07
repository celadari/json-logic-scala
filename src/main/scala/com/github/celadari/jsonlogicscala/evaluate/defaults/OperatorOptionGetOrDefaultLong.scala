package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object OperatorOptionGetOrDefaultLong extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(l) => {
        l match {
          case _: Long => l
          case _: java.lang.Long => l
          case _ => throw new IllegalInputException(s"Operator OptionToLong can only be applied to Option[Long] or Long. Input condition: $value")
        }
      }
      case None => 0L
      case _: Long => value
      case _: java.lang.Long => value
      case _ => throw new IllegalInputException(s"Operator OptionToLong can only be applied to Option[Long] or Long. Input condition: $value")
    }
  }
}
