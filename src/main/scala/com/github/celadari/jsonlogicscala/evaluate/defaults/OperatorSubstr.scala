package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator
import com.github.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException

object OperatorSubstr extends Operator {

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
