// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultString extends UnaryOperator {

  /**
   * Returns string value from Option.
   * Returns value itself if value is a String.
   * Returns empty string if provided None input value.
   * @param value: value operator operates on.
   * @return string.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[String] nor a String type.
   */
  def unaryOperator(value: Any): Any = {
    value match {
      case Some(s) => {
        s match {
          case string: String => string
          case _ => throw new IllegalInputException(s"Operator OptionToString can only be applied to Option[String] or String. Input condition: $value")
        }
      }
      case None => ""
      case string: String => string
      case _ => throw new IllegalInputException(s"Operator OptionToString can only be applied to Option[String] or String. Input condition: $value")
    }
  }
}
