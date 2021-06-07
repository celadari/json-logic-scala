package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.JsNumber
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerLong extends AnyFlatSpec with Matchers {

  "Marshall Long value" should "return value" in {
    MarshallerLong.marshal(5L) shouldBe JsNumber(5L)
  }

  "Marshall java.lang.Long value" should "return value" in {
    MarshallerLong.marshal(5L: java.lang.Long) shouldBe JsNumber(5L)
  }

  "Marshall non Long value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerLong.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerLong: null.
                                 |MarshallerLong can only be applied to Long values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
