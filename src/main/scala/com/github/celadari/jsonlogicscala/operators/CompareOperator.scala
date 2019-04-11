package com.github.celadari.jsonlogicscala.operators


object CompareOperator {
  implicit val cmpOperator: CompareOperator = new CompareOperator
}

class CompareOperator {

  // custom operators to be override by user
  def negateCustom(value: Any): Any = throw new IllegalArgumentException(s"Invalid argument: $value")
  def cmpCustomLong(a: Long, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: $b")
  def cmpCustomDouble(a: Double, b: Any) = throw new IllegalArgumentException(s"Invalid argument: $b")
  def cmpCustom(a: Any, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")
  def eqCustomLong(a: Long, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: $b")
  def eqCustomDouble(a: Double, b: Any) = throw new IllegalArgumentException(s"Invalid argument: $b")
  def eqCustom(a: Any, b: Any): Any = throw new IllegalArgumentException(s"Invalid argument: ${(a, b)}")

  // operators for user
  def lt(a: Any, b: Any): Any = cmp(a, b)
  def lteq(a: Any, b: Any): Any = negate(gt(a, b))
  def gteq(a: Any, b: Any): Any = negate(lt(a, b))
  def gt(a: Any, b: Any): Any = lt(b, a)

  def diff(a: Any, b: Any): Any = negate(eq(a, b))

  def eq(a: Any, b: Any): Any = {
    a match{
      case a: Byte => eqLong(a.asInstanceOf[Long], b)
      case a: Short => eqLong(a.asInstanceOf[Long], b)
      case a: Int => eqLong(a.asInstanceOf[Long], b)
      case a: Long => eqLong(a, b)
      case a: Float => eqDouble(a.asInstanceOf[Double], b)
      case a: Double => eqDouble(a, b)
      case other => eqCustom(other, b)
    }
  }

  def negate(value: Any): Any = {
    value match {
      case value: Boolean => !value
      case other => negateCustom(other)
    }
  }

  def eqLong(a: Long, b: Any): Any = {
    b match {
      case b: Byte => a == b
      case b: Short => a == b
      case b: Int => a == b
      case b: Long => a == b
      case b: Float => a == b
      case b: Double => a == b
      case other => eqCustomLong(a, other)
    }
  }

  def eqDouble(a: Double, b: Any): Any = {
    b match {
      case b: Byte => a == b
      case b: Short => a == b
      case b: Int => a == b
      case b: Long => a == b
      case b: Float => a == b
      case b: Double => a == b
      case other => eqCustomDouble(a, other)
    }
  }

  def cmpLong(a: Long, b: Any): Any = {
    b match {
      case b: Byte => a < b
      case b: Short => a < b
      case b: Int => a < b
      case b: Long => a < b
      case b: Float => a < b
      case b: Double => a < b
      case other => cmpCustomLong(a, other)
    }
  }

  def cmpDouble(a: Double, b: Any): Any = {
    b match {
      case b: Byte => a < b
      case b: Short => a < b
      case b: Int => a < b
      case b: Long => a < b
      case b: Float => a < b
      case b: Double => a < b
      case other => cmpCustomDouble(a, other)
    }
  }

  def cmp(a: Any, b: Any): Any = {
    a match {
      case a: Byte => cmpLong(a.asInstanceOf[Long], b)
      case a: Short => cmpLong(a.asInstanceOf[Long], b)
      case a: Int => cmpLong(a.asInstanceOf[Long], b)
      case a: Long => cmpLong(a, b)
      case a: Float => cmpDouble(a.asInstanceOf[Double], b)
      case a: Double => cmpDouble(a, b)
      case other => cmpCustom(other, b)
    }
  }
}