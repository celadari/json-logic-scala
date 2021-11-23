// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultShort extends UnaryOperator {

  /**
   * Returns short value from Option.
   * Returns value itself if value is a Short.
   * Returns 0 if provided None input value.
   * @param value: value operator operates on.
   * @return short.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Short ] nor a Short type.
   */
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
