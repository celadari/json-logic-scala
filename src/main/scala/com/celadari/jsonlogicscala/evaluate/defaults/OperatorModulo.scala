package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


object OperatorModulo extends Operator {

  def $percent(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Byte = (num1 % num2).toByte
  def $percent(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Short = (num1 % num2).toShort
  def $percent(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = num1 % num2
  def $percent(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Byte, num2: java.lang.Float): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Byte, num2: java.lang.Double): java.lang.Double = num1 % num2

  def $percent(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Short = (num1 % num2).toShort
  def $percent(num1: java.lang.Short, num2: java.lang.Short): java.lang.Short = (num1 % num2).toShort
  def $percent(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = num1 % num2
  def $percent(num1: java.lang.Short, num2: java.lang.Long): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Short, num2: java.lang.Float): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Short, num2: java.lang.Double): java.lang.Double = num1 % num2

  def $percent(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = num1 % num2
  def $percent(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = num1 % num2
  def $percent(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = num1 % num2
  def $percent(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Integer, num2: java.lang.Float): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Integer, num2: java.lang.Double): java.lang.Double = num1 % num2

  def $percent(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = num1 % num2
  def $percent(num1: java.lang.Long, num2: java.lang.Float): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Long, num2: java.lang.Double): java.lang.Double = num1 %  num2

  def $percent(num1: java.lang.Float, num2: java.lang.Byte): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Float, num2: java.lang.Short): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Float, num2: java.lang.Integer): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Float, num2: java.lang.Long): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Float, num2: java.lang.Float): java.lang.Float = num1 % num2
  def $percent(num1: java.lang.Float, num2: java.lang.Double): java.lang.Double = num1 % num2

  def $percent(num1: java.lang.Double, num2: java.lang.Byte): java.lang.Double = num1 % num2
  def $percent(num1: java.lang.Double, num2: java.lang.Short): java.lang.Double = num1 % num2
  def $percent(num1: java.lang.Double, num2: java.lang.Integer): java.lang.Double = num1 % num2
  def $percent(num1: java.lang.Double, num2: java.lang.Long): java.lang.Double = num1 % num2
  def $percent(num1: java.lang.Double, num2: java.lang.Float): java.lang.Double = num1 % num2
  def $percent(num1: java.lang.Double, num2: java.lang.Double): java.lang.Double = num1 % num2
}
