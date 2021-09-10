package com.celadari.jsonlogicscala.evaluate.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


trait TestBoolean extends AnyFlatSpec with Matchers {

  val xBool: java.lang.Boolean = true
  val yBool: java.lang.Boolean = false
  val zBool: java.lang.Boolean = true
  val uBool: java.lang.Boolean = false
  val vBool: java.lang.Boolean = false

}
