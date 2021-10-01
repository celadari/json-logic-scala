// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorIn extends Operator {

  implicit class ArrayToListConverter(valueToConvert: Any) {

    def transformArrayToList: Any = transformArrayToList(valueToConvert)

    private[this] def transformArrayToList(value: Any): Any = {
      value match {
        case arr: Array[_] => arr.map(transformArrayToList).toList
        case map: Map[_, _] => map.map{case (key, value) => (transformArrayToList(key), transformArrayToList(value))}
        case iter: Iterable[_] => iter.map(transformArrayToList).toList
        case other => other
      }
    }
  }

  /**
   * Returns boolean if element is in array.
   * First condition is value to look if is inside array, second condition is array.
   * @param values: conditions.
   * @return true if element in array, false otherwise.
   */
  def in(values: Array[java.lang.Object]): java.lang.Boolean = {

    if (values.length != 2) {
      val valString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"In operator array requires length of conditions input to be exactly 2. \nArray of conditions: $valString")
    }

    val value = values(0).transformArrayToList
    val list = values(1).transformArrayToList.asInstanceOf[List[Any]]
    list.contains(value)
  }

  /**
   * Returns boolean if element is in string.
   * First condition is substring to look if is inside string, second condition is string.
   * @param values: conditions.
   * @return true if element is substring of string, false otherwise.
   */
  def in(values: Array[java.lang.String]): java.lang.Boolean = {

    if (values.length != 2) {
      val valString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"In operator string requires length of conditions input to be exactly 2. \nArray of conditions: $valString")
    }

    val subString = values(0)
    val string = values(1)
    string.contains(subString)
  }
}
