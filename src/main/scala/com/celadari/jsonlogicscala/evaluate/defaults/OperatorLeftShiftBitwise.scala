// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of Bitwise LEFT SHIFT <<.
 */
object OperatorLeftShiftBitwise extends Operator {

  def $less$less(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Integer = num1 << num2.toInt
  def $less$less(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Integer = num1 << num2.toInt
  def $less$less(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = num1 << num2
  def $less$less(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Integer = num1 << num2.toInt

  def $less$less(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Integer = num1 << num2.toInt
  def $less$less(num1: java.lang.Short, num2: java.lang.Short): java.lang.Integer = num1 << num2.toInt
  def $less$less(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = num1 << num2
  def $less$less(num1: java.lang.Short, num2: java.lang.Long): java.lang.Integer = num1 << num2.toInt

  def $less$less(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = num1 << num2.toInt
  def $less$less(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = num1 << num2.toInt
  def $less$less(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = num1 << num2
  def $less$less(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Integer = num1 << num2.toInt

  def $less$less(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = num1 << num2.toInt
  def $less$less(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = num1 << num2.toInt
  def $less$less(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = num1 << num2
  def $less$less(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = num1 << num2
}
