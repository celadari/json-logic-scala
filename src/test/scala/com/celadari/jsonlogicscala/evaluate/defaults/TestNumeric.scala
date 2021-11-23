// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


trait TestNumeric extends AnyFlatSpec with Matchers {

  val xByte: java.lang.Byte = 5.toByte
  val xShort: java.lang.Short = 5.toShort
  val xInt: java.lang.Integer = 5
  val xLong: java.lang.Long = 5L
  val xFloat: java.lang.Float = 5f
  val xDouble: java.lang.Double = 5d

  val yByte: java.lang.Byte = 47.toByte
  val yShort: java.lang.Short = 47.toShort
  val yInt: java.lang.Integer = 47
  val yLong: java.lang.Long = 47L
  val yFloat: java.lang.Float = 47f
  val yDouble: java.lang.Double = 47d

}
