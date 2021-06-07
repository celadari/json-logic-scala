package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorPow extends Operator {

  private[defaults] def pow(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = {
    var num = 1
    for (_ <- 0 until num2.toInt) num *= num1
    num
  }
  private[defaults] def pow(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = {
    var num = 1L
    for (_ <- 0 until num2.toInt) num *= num1
    num
  }

  def $times$times(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Integer = pow(num1.toInt, num2.toInt)
  def $times$times(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Integer = pow(num1.toInt, num2.toInt)
  def $times$times(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = pow(num1.toInt, num2)
  def $times$times(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Long = pow(num1.toLong, num2)
  def $times$times(num1: java.lang.Byte, num2: java.lang.Float): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Byte, num2: java.lang.Double): java.lang.Double = math.pow(num1.toDouble, num2)

  def $times$times(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Integer = pow(num1.toInt, num2.toInt)
  def $times$times(num1: java.lang.Short, num2: java.lang.Short): java.lang.Integer = pow(num1.toInt, num2.toInt)
  def $times$times(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = pow(num1.toInt, num2)
  def $times$times(num1: java.lang.Short, num2: java.lang.Long): java.lang.Long = pow(num1.toLong, num2)
  def $times$times(num1: java.lang.Short, num2: java.lang.Float): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Short, num2: java.lang.Double): java.lang.Double = math.pow(num1.toDouble, num2)

  def $times$times(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = pow(num1, num2.toInt)
  def $times$times(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = pow(num1, num2.toInt)
  def $times$times(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = pow(num1, num2)
  def $times$times(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Long = pow(num1.toLong, num2)
  def $times$times(num1: java.lang.Integer, num2: java.lang.Float): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Integer, num2: java.lang.Double): java.lang.Double = math.pow(num1.toDouble, num2)

  def $times$times(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = pow(num1, num2.toLong)
  def $times$times(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = pow(num1, num2.toLong)
  def $times$times(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = pow(num1, num2.toLong)
  def $times$times(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = pow(num1, num2)
  def $times$times(num1: java.lang.Long, num2: java.lang.Float): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Long, num2: java.lang.Double): java.lang.Double = math.pow(num1.toDouble, num2)

  def $times$times(num1: java.lang.Float, num2: java.lang.Byte): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Float, num2: java.lang.Short): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Float, num2: java.lang.Integer): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Float, num2: java.lang.Long): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Float, num2: java.lang.Float): java.lang.Double = math.pow(num1.toDouble, num2.toDouble)
  def $times$times(num1: java.lang.Float, num2: java.lang.Double): java.lang.Double = math.pow(num1.toDouble, num2)

  def $times$times(num1: java.lang.Double, num2: java.lang.Byte): java.lang.Double = math.pow(num1, num2.toDouble)
  def $times$times(num1: java.lang.Double, num2: java.lang.Short): java.lang.Double = math.pow(num1, num2.toDouble)
  def $times$times(num1: java.lang.Double, num2: java.lang.Integer): java.lang.Double = math.pow(num1, num2.toDouble)
  def $times$times(num1: java.lang.Double, num2: java.lang.Long): java.lang.Double = math.pow(num1, num2.toDouble)
  def $times$times(num1: java.lang.Double, num2: java.lang.Float): java.lang.Double = math.pow(num1, num2.toDouble)
  def $times$times(num1: java.lang.Double, num2: java.lang.Double): java.lang.Double = math.pow(num1, num2)
}
