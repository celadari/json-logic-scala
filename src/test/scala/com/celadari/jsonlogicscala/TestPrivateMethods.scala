package com.celadari.jsonlogicscala

import org.scalatest.PrivateMethodTester
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


trait TestPrivateMethods extends AnyFlatSpec with PrivateMethodTester with Matchers {
  def toSymbol(string: String): Symbol = Symbol(string)
}
