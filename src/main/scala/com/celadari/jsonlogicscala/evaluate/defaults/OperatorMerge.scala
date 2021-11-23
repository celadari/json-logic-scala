// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.converters.CollectionConverters.HasFlatMap


object OperatorMerge extends Operator {

  /**
   * Returns merged array from values.
   * If "values" contains arrays and simple values, they are all flatened together.
   * @example Array(5, Array(4, 5), Array(6, 7), 8) will return Array(5, 4, 5, 6, 7, 8).
   * @param values: array of arrays to be merged together.
   * @return array of merged arrays.
   */
  def merge(values: Array[Any]): Array[Any] = {
    values.flatMapAccordingToScalaVersion[Any]{
      case arr: Array[_] => arr
      case _: Map[_, _] => throw new IllegalInputException("Merge operator does not accept map as input condition")
      case other => Array(other)
    }
  }
}
