package com.github.celadari.jsonlogicscala.operators

object BooleanOperator {
  implicit val booleanOperator: BooleanOperator = new BooleanOperator
}


class BooleanOperator {

  // custom operators to be override by user
  def andCustom(a: Any, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")
  def andCustomBoolean(a: Boolean, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")
  def orCustom(a: Any, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")
  def orCustomBoolean(a: Boolean, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")
  def negateCustom(value: Any): Any = throw new IllegalArgumentException(s"Invalid argument: $value")

  // operators for user
  def negate(value: Any): Any = {
    value match {
      case value: Boolean => !value
      case other => negateCustom(other)
    }
  }

  def andBoolean(a: Boolean, b: Any): Any = {
    b match {
      case b: Boolean => a && b
      case other => andCustomBoolean(a, other)
    }
  }

  def orBoolean(a: Boolean, b: Any): Any = {
    b match {
      case b: Boolean => a || b
      case other => orCustomBoolean(a, other)
    }
  }

  def and(a: Any, b: Any): Any = {
    a match {
      case a: Boolean => andBoolean(a, b)
      case other => andCustom(other, b)
    }
  }

  def or(a: Any, b: Any): Any = {
    a match {
      case a: Boolean => orBoolean(a, b)
      case other => orCustom(other, b)
    }
  }

  def xor(a: Any, b: Any): Any = or(and(negate(a), b), and(a, negate(b)))
}
