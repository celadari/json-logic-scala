package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.converters.CollectionConverters.HasPartitionMap
import com.celadari.jsonlogicscala.evaluate.Operator
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorIfElse extends Operator {

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
