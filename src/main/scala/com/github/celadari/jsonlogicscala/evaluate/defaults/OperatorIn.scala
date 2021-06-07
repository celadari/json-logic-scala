package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator
import com.github.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException

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

  def in(values: Array[java.lang.Object]): java.lang.Boolean = {

    if (values.length != 2) {
      val valString = values.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"In operator array requires length of conditions input to be exactly 2. \nArray of conditions: $valString")
    }

    val value = values(0).transformArrayToList
    val list = values(1).transformArrayToList.asInstanceOf[List[Any]]
    list.contains(value)
  }

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
