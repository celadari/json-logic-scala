package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.github.celadari.jsonlogicscala.converters.CollectionConverters.HasFlatMap

object OperatorMerge extends Operator {

  def merge(values: Array[Any]): Array[Any] = {
    values.flatMapAccordingToScalaVersion[Any]{
      case arr: Array[_] => arr
      case _: Map[_, _] => throw new IllegalInputException("Merge operator does not accept map as input condition")
      case other => Array(other)
    }
  }
}
