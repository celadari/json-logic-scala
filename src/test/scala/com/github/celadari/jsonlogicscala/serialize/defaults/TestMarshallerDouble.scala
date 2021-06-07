package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.JsNumber
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerDouble extends AnyFlatSpec with Matchers {

  "Marshall Double value" should "return value" in {
    MarshallerDouble.marshal(5d) shouldBe JsNumber(5d)
  }

  "Marshall java.lang.Double value" should "return value" in {
    MarshallerDouble.marshal(5d: java.lang.Double) shouldBe JsNumber(5d)
  }

  "Marshall non Double value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerDouble.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerDouble: null.
                                 |MarshallerDouble can only be applied to Double values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
