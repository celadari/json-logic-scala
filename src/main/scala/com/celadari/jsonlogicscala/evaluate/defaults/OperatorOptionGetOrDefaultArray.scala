// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultArray extends UnaryOperator {

  /**
   * Returns array value from Option.
   * Returns value itself if value is an Array.
   * Returns empty array if provided None input value.
   * @param value: value operator operates on.
   * @return array.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Array[_] ] nor a Array[_] type.
   */
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
