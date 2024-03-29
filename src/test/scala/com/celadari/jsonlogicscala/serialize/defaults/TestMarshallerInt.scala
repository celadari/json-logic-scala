// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsNumber
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerInt extends AnyFlatSpec with Matchers {

  "Marshall Int value" should "return value" in {
    MarshallerInt.marshal(5) shouldBe JsNumber(5)
  }

  "Marshall java.lang.Int value" should "return value" in {
    MarshallerInt.marshal(5: java.lang.Integer) shouldBe JsNumber(5)
  }

  "Marshall non Int value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerInt.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerInt: null.
                                 |MarshallerInt can only be applied to Int values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
