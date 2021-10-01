// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of Bitwise OR.
 */
object OperatorOrBitwise extends Operator {

  def $bar(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Long = num1 | num2

  def $bar(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Short, num2: java.lang.Short): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Short, num2: java.lang.Long): java.lang.Long = num1 | num2
  def $bar(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = num1 | num2
  def $bar(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Long = num1 | num2
  def $bar(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = num1 | num2
  def $bar(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = num1 | num2
  def $bar(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = num1 | num2
  def $bar(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = num1 | num2
}
