package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


object OperatorOptionGetOrDefaultMap extends UnaryOperator {

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
