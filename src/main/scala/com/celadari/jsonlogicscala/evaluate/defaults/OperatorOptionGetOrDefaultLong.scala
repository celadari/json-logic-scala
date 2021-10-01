// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultLong extends UnaryOperator {

  /**
   * Returns long value from Option.
   * Returns value itself if value is a Long.
   * Returns 0 if provided None input value.
   * @param value: value operator operates on.
   * @return long.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Long] nor a Long type.
   */
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
