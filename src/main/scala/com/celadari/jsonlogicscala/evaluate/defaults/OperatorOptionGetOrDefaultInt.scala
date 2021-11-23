// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultInt extends UnaryOperator {

  /**
   * Returns int value from Option.
   * Returns value itself if value is an Int.
   * Returns 0 if provided None input value.
   * @param value: value operator operates on.
   * @return int.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Int]  nor a Int type.
   */
  def unaryOperator(value: Any): Any = {
    value match {
      case Some(i) => {
        i match {
          case _: Int => i
          case _: java.lang.Integer => i
          case _ => throw new IllegalInputException(s"Operator OptionToInt can only be applied to Option[Int] or Int. Input condition: $value")
        }
      }
      case None => 0
      case _: Int => value
      case _: java.lang.Integer => value
      case _ => throw new IllegalInputException(s"Operator OptionToInt can only be applied to Option[Int] or Int. Input condition: $value")
    }
  }
}
