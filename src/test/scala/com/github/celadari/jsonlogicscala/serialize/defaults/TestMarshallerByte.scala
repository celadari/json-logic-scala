package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.JsNumber
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


class TestMarshallerByte extends AnyFlatSpec with Matchers {

  "Marshall byte value" should "return value" in {
    MarshallerByte.marshal(5.toByte) shouldBe JsNumber(5.toByte)
  }

  "Marshall java.lang.Byte value" should "return value" in {
    MarshallerByte.marshal(5.toByte: java.lang.Byte) shouldBe JsNumber(5.toByte)
  }

  "Marshall non byte value" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {MarshallerByte.marshal(null)}
    val expectedErrorMessage = """Illegal input argument to MarshallerByte: null.
                                 |MarshallerByte can only be applied to byte values.
                                 |Check if valueOpt and typeCodenameOpt of ValueLogic are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }
}
