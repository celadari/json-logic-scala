package com.celadari.jsonlogicscala.serialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsNumber
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerFloat extends AnyFlatSpec with Matchers {

  "Marshall Float value" should "return value" in {
    MarshallerFloat.marshal(5f) shouldBe JsNumber(5f)
  }

  "Marshall java.lang.Float value" should "return value" in {
    MarshallerFloat.marshal(5f: java.lang.Float) shouldBe JsNumber(5f)
  }

  "Marshall non Float value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerFloat.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerFloat: null.
                                 |MarshallerFloat can only be applied to Float values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
