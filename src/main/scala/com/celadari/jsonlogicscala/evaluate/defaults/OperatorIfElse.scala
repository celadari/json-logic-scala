// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.converters.CollectionConverters.HasPartitionMap
import com.celadari.jsonlogicscala.evaluate.Operator
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorIfElse extends Operator {

  /**
   * Returns a value after performing if else statement on input values.
   * On case length 3, first element is boolean, second element is value to return if boolean is true, third element
   * is value to return if boolean is false.
   * On case length 5 and greater, first element is boolean, second element is value to return if boolean is true,
   * third element is another boolean condition if first element is false, fourth element is to be returned if third
   * element is true, and so on...
   * @param values: input array of boolean and return values.
   * @return value associated with boolean true.
   */
  def ifElse(values: Array[java.lang.Object]): java.lang.Object = {
    val n = values.length

    if (n < 3) {
      val valString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"IfElse operator requires length of conditions to be at least 3. \nArray of conditions: $valString")
    }

    if (n % 2 == 0) {
      val valString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"IfElse operator requires length of conditions to be odd. \nArray of conditions: $valString")
    }

    val (boolValues, returnValues) = values.zipWithIndex.partitionMapAccordingToScalaVersion{
      case (boolVal, idx) if (idx % 2 == 0 && idx < n - 1) => Left(boolVal.asInstanceOf[java.lang.Boolean])
      case (returnVal, _) => Right(returnVal)
    }

    var i = 0
    while (i < n / 2 && !boolValues(i)) {
      i += 1
    }

    returnValues(i)
  }
}
