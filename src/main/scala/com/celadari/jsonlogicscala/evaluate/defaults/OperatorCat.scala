// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


object OperatorCat extends Operator {

  /**
   * Returns stringified version of array of elements.
   * @param values: array of elements to be concatenated into string.
   * @return string from elements concatenation.
   */
  def cat(values: Array[Any]): java.lang.String = values.mkString
}
