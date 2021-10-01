// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorSubstr extends Operator {

  /**
   * Returns sliced string from start index to optional final index.
   * Throws an exception if input array values' length is less than 2 or greater than 3.
   * First element of "values" is string to be sliced, second element is start index to slice, third element is a length
   * meaning number of characters to return.
   * A negative start index starts backwards from the end of the string.
   * A negative length means to stop length many characters before the end.
   * @param values: values operator operates on.
   * @return sliced string.
   */
  def substr(values: Array[java.lang.Object]): java.lang.String = {

    if (values.length < 2 || values.length > 3) {
      val valString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException("Subst operator requires length of conditions input to be at least 2 and not greater than 3. " +
        s"\nArray of conditions: $valString")
    }

    val string = values(0).asInstanceOf[java.lang.String]
    val length = string.length
    val idx = values(1).asInstanceOf[java.lang.Number].intValue()
    val len = values.lift(2).map(_.asInstanceOf[java.lang.Number].intValue()).getOrElse(length - idx)
    val startIdx = if (idx < 0) (idx + length) % length else idx
    val endIdx = if (len < 0) (length + len) else (startIdx + len)

    string.slice(startIdx, endIdx)
  }
}
