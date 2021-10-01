// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultByte extends UnaryOperator {

  /**
   * Returns byte value from Option.
   * Returns value itself if value is a Byte.
   * Returns 0 if provided None input value.
   * @param value: value operator operates on.
   * @return byte.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Byte]  nor a Byte type.
   */
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
