package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultArray extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(arr) => {
        arr match {
          case _: Array[_] => arr
          case _ => throw new IllegalInputException(s"Operator OptionToArray can only be applied to Option[Array[_]] or Array[_]. Input condition: $value")
        }
      }
      case None => Array[Any]()
      case _: Array[_] => value
      case _ => throw new IllegalInputException(s"Operator OptionToArray can only be applied to Option[Array[_]] or Array[_]. Input condition: $value")
    }
  }
}
