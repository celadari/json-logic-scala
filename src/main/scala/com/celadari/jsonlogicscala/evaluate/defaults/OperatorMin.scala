// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.Operator


/**
 * Reduce type operator of MIN Number.
 */
object OperatorMin extends Operator {

  def min(num1: java.lang.Byte, num2: java.lang.Byte): java.lang.Byte = if (num1 <= num2) num1 else num2
  def min(num1: java.lang.Byte, num2: java.lang.Short): java.lang.Short = if (num1 <= num2) num1.shortValue else num2
  def min(num1: java.lang.Byte, num2: java.lang.Integer): java.lang.Integer = if (num1 <= num2) num1.intValue.intValue else num2
  def min(num1: java.lang.Byte, num2: java.lang.Long): java.lang.Long = if (num1 <= num2) num1.longValue else num2
  def min(num1: java.lang.Byte, num2: java.lang.Float): java.lang.Float = if (num1 <= num2) num1.floatValue else num2
  def min(num1: java.lang.Byte, num2: java.lang.Double): java.lang.Double = if (num1 <= num2) num1.doubleValue else num2

  def min(num1: java.lang.Short, num2: java.lang.Byte): java.lang.Short = if (num1 <= num2) num1 else num2.shortValue
  def min(num1: java.lang.Short, num2: java.lang.Short): java.lang.Short = if (num1 <= num2) num1 else num2
  def min(num1: java.lang.Short, num2: java.lang.Integer): java.lang.Integer = if (num1 <= num2) num1.intValue else num2
  def min(num1: java.lang.Short, num2: java.lang.Long): java.lang.Long = if (num1 <= num2) num1.longValue else num2
  def min(num1: java.lang.Short, num2: java.lang.Float): java.lang.Float = if (num1 <= num2) num1.floatValue else num2
  def min(num1: java.lang.Short, num2: java.lang.Double): java.lang.Double = if (num1 <= num2) num1.doubleValue else num2

  def min(num1: java.lang.Integer, num2: java.lang.Byte): java.lang.Integer = if (num1 <= num2) num1 else num2.intValue
  def min(num1: java.lang.Integer, num2: java.lang.Short): java.lang.Integer = if (num1 <= num2) num1 else num2.intValue
  def min(num1: java.lang.Integer, num2: java.lang.Integer): java.lang.Integer = if (num1 <= num2) num1.intValue else num2
  def min(num1: java.lang.Integer, num2: java.lang.Long): java.lang.Long = if (num1 <= num2) num1.longValue else num2
  def min(num1: java.lang.Integer, num2: java.lang.Float): java.lang.Float = if (num1 <= num2) num1.floatValue else num2
  def min(num1: java.lang.Integer, num2: java.lang.Double): java.lang.Double = if (num1 <= num2) num1.doubleValue else num2

  def min(num1: java.lang.Long, num2: java.lang.Byte): java.lang.Long = if (num1 <= num2) num1 else num2.longValue
  def min(num1: java.lang.Long, num2: java.lang.Short): java.lang.Long = if (num1 <= num2) num1 else num2.longValue
  def min(num1: java.lang.Long, num2: java.lang.Integer): java.lang.Long = if (num1 <= num2) num1 else num2.longValue
  def min(num1: java.lang.Long, num2: java.lang.Long): java.lang.Long = if (num1 <= num2) num1.longValue else num2
  def min(num1: java.lang.Long, num2: java.lang.Float): java.lang.Float = if (num1 <= num2) num1.floatValue else num2
  def min(num1: java.lang.Long, num2: java.lang.Double): java.lang.Double = if (num1 <= num2) num1.doubleValue else num2

  def min(num1: java.lang.Float, num2: java.lang.Byte): java.lang.Float = if (num1 <= num2) num1 else num2.floatValue
  def min(num1: java.lang.Float, num2: java.lang.Short): java.lang.Float = if (num1 <= num2) num1 else num2.floatValue
  def min(num1: java.lang.Float, num2: java.lang.Integer): java.lang.Float = if (num1 <= num2) num1 else num2.floatValue
  def min(num1: java.lang.Float, num2: java.lang.Long): java.lang.Float = if (num1 <= num2) num1 else num2.floatValue
  def min(num1: java.lang.Float, num2: java.lang.Float): java.lang.Float = if (num1 <= num2) num1.floatValue else num2
  def min(num1: java.lang.Float, num2: java.lang.Double): java.lang.Double = if (num1 <= num2) num1.doubleValue else num2

  def min(num1: java.lang.Double, num2: java.lang.Byte): java.lang.Double = if (num1 <= num2) num1 else num2.doubleValue
  def min(num1: java.lang.Double, num2: java.lang.Short): java.lang.Double = if (num1 <= num2) num1 else num2.doubleValue
  def min(num1: java.lang.Double, num2: java.lang.Integer): java.lang.Double = if (num1 <= num2) num1 else num2.doubleValue
  def min(num1: java.lang.Double, num2: java.lang.Long): java.lang.Double = if (num1 <= num2) num1 else num2.doubleValue
  def min(num1: java.lang.Double, num2: java.lang.Float): java.lang.Double = if (num1 <= num2) num1 else num2.doubleValue
  def min(num1: java.lang.Double, num2: java.lang.Double): java.lang.Double = if (num1 <= num2) num1.doubleValue else num2
}
