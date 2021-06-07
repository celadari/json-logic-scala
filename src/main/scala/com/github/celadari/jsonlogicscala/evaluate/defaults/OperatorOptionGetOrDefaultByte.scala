package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

object OperatorOptionGetOrDefaultByte extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case Some(byte) => {
        byte match {
          case _: Byte => byte
          case _: java.lang.Byte => byte
          case _ => throw new IllegalInputException(s"Operator OptionToByte can only be applied to Option[Byte] or Byte. Input condition: $value")
        }
      }
      case None => 0.toByte
      case _: Byte => value
      case _: java.lang.Byte => value
      case _ => throw new IllegalInputException(s"Operator OptionToByte can only be applied to Option[Byte] or Byte. Input condition: $value")
    }
  }
}
