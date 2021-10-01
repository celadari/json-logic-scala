// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultDouble extends UnaryOperator {

  /**
   * Returns double value from Option.
   * Returns value itself if value is a Double.
   * Returns 0 if provided None input value.
   * @param value: value operator operates on.
   * @return double.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Double]  nor a Double type.
   */
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
