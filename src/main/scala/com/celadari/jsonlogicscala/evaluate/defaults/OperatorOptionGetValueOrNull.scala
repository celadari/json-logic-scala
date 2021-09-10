package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator


object OperatorOptionGetValueOrNull extends UnaryOperator {

  def unaryOperator(value: Any): Any = {
    value match {
      case opt: Option[_] => opt.orNull
      case other => other
    }
  }
}
