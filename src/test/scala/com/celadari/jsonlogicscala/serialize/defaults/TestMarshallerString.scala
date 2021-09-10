package com.celadari.jsonlogicscala.serialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsString
import com.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerString extends AnyFlatSpec with Matchers {

  "Marshall string value" should "return value" in {
    MarshallerString.marshal("I love Paris") shouldBe JsString("I love Paris")
  }

  "Marshall string value again" should "return value" in {
    MarshallerString.marshal("What a day") shouldBe JsString("What a day")
  }

  "Marshall non string value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerString.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerString: null.
                                 |MarshallerString can only be applied to string values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
