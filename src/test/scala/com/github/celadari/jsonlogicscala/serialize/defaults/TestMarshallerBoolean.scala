package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.JsBoolean
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException

class TestMarshallerBoolean extends AnyFlatSpec with Matchers {

  "Marshall boolean value" should "return value" in {
    MarshallerBoolean.marshal(true) shouldBe JsBoolean(true)
  }

  "Marshall java.lang.Boolean value" should "return value" in {
    MarshallerBoolean.marshal(true: java.lang.Boolean) shouldBe JsBoolean(true)
  }

  "Marshall non boolean value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerBoolean.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerBoolean: null.
                                 |MarshallerBoolean can only be applied to boolean values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
