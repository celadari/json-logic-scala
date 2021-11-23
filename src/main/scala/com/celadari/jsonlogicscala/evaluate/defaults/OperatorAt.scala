// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator
import com.celadari.jsonlogicscala.exceptions.{IllegalInputException, WrongNumberOfConditionsException}


object OperatorAt extends Operator {

  /**
   * Returns value at given position inside array.
   * First condition is index to look at, second condition is array to be applied index.
   * @param values: conditions.
   * @return selected element from array.
   */
  def at(values: Array[Any]): Any = {

    if (values.length != 2) {
      val condString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"At operator requires length of conditions input to be exactly 2. \nArray of conditions: $condString")
    }

    val index = values(0)

    values(1) match {
      case arr: Array[Any] => arr.apply(index.toString.toDouble.toInt)
      case map: Map[_, _] => map.asInstanceOf[Map[Any, Any]].apply(index)
      case ite: Iterable[Any] => ite.toArray.apply(index.toString.toDouble.toInt)
      case any: Any => throw new IllegalInputException(s"At operator second input must be either: Array, Map, Iterable. Current second input: ${any.toString}")
    }
  }
}
