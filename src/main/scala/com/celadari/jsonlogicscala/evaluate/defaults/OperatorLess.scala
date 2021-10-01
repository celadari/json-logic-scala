// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of Comparison <.
 */
object OperatorLess extends Operator {

  def $less(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Byte, num2: java.lang.Float): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Byte, num2: java.lang.Double): java.lang.Boolean = num1 < num2

  def $less(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Short, num2: java.lang.Short): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Short, num2: java.lang.Long): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Short, num2: java.lang.Float): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Short, num2: java.lang.Double): java.lang.Boolean = num1 < num2

  def $less(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Integer, num2: java.lang.Float): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Integer, num2: java.lang.Double): java.lang.Boolean = num1 < num2

  def $less(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Long, num2: java.lang.Short): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Long, num2: java.lang.Long): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Long, num2: java.lang.Float): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Long, num2: java.lang.Double): java.lang.Boolean = num1 <  num2

  def $less(num1: java.lang.Float, num2: java.lang.Byte): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Float, num2: java.lang.Short): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Float, num2: java.lang.Integer): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Float, num2: java.lang.Long): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Float, num2: java.lang.Float): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Float, num2: java.lang.Double): java.lang.Boolean = num1 < num2

  def $less(num1: java.lang.Double, num2: java.lang.Byte): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Double, num2: java.lang.Short): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Double, num2: java.lang.Integer): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Double, num2: java.lang.Long): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Double, num2: java.lang.Float): java.lang.Boolean = num1 < num2
  def $less(num1: java.lang.Double, num2: java.lang.Double): java.lang.Boolean = num1 < num2
}
