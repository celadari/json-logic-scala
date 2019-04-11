package com.github.celadari.jsonlogicscala.operators

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object ContainsOperator {
  implicit val containsOperator: ContainsOperator = new ContainsOperator
}

class ContainsOperator {

  // custom operators to be override by user
  def containsCustom(a: Any, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")
  def negateCustom(value: Any): Any = throw new IllegalArgumentException(s"Invalid argument: $value")

  // operators for user
  def containsNot(a: Any, b: Any): Any = negate(contains(a, b))

  def negate(value: Any): Any = {
    value match {
      case value: Boolean => !value
      case other => negateCustom(other)
    }
  }

  def contains(a: Any, b: Any): Any = {
    a match {
      case a: Array[_] => a.contains(b)
      case a: List[_] => a.contains(b)
      case a: ArrayBuffer[_] => a.contains(b)
      case a: mutable.Seq[_] => a.contains(b)
      case a: ListBuffer[_] => a.contains(b)
      case a: mutable.Stack[_] => a.contains(b)
      case a: mutable.Queue[_] => a.contains(b)
      case other => containsCustom(other, b)
    }
  }
}