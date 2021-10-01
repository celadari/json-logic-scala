// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsNumber
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerShort extends AnyFlatSpec with Matchers {

  "Marshall short value" should "return value" in {
    MarshallerShort.marshal(5.toShort) shouldBe JsNumber(5.toShort)
  }

  "Marshall java.lang.Short value" should "return value" in {
    MarshallerShort.marshal(5.toShort: java.lang.Short) shouldBe JsNumber(5.toShort)
  }

  "Marshall non short value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerShort.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerShort: null.
                                 |MarshallerShort can only be applied to short values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
