package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorCat extends Operator {

  def cat(values: Array[Any]): java.lang.String = values.mkString
}
