// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultMap extends UnaryOperator {

  /**
   * Returns map value from Option.
   * Returns value itself if value is a Map[_, _] .
   * Returns empty map if provided None input value.
   * @param value: value operator operates on.
   * @return map.
   * @note throws an [[com.celadari.jsonlogicscala.exceptions.IllegalInputException]] if input is neither an
   *       Option[Map[_, _] ] nor a Map[_, _] type.
   */
  def unaryOperator(value: Any): Any = {
    value match {
      case Some(map) => {
        map match {
          case _: Map[_, _] => map
          case _ => throw new IllegalInputException(s"Operator OptionToMap can only be applied to Option[Map[_, _]] or Map[_, _]. Input condition: $value")
        }
      }
      case None => Map[Any, Any]()
      case _: Map[_, _] => value
      case _ => throw new IllegalInputException(s"Operator OptionToMap can only be applied to Option[Map[_, _]] or Map[_, _]. Input condition: $value")
    }
  }
}
