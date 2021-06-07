package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorAndBitwise extends Operator {

  def $amp(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Byte = (num1 & num2).toByte
  def $amp(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Short = (num1 & num2).toShort
  def $amp(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = num1 & num2
  def $amp(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Long = num1 & num2
  def $amp(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Short = (num1 & num2).toShort
  def $amp(num1: java.lang.Short, num2: java.lang.Short): java.lang.Short = (num1 & num2).toShort
  def $amp(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = num1 & num2
  def $amp(num1: java.lang.Short, num2: java.lang.Long): java.lang.Long = num1 & num2
  def $amp(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = num1 & num2
  def $amp(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = num1 & num2
  def $amp(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = num1 & num2
  def $amp(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Long = num1 & num2
  def $amp(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = num1 & num2
  def $amp(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = num1 & num2
  def $amp(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = num1 & num2
  def $amp(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = num1 & num2
}
