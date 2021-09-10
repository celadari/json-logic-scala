package com.celadari.jsonlogicscala.deserialize.defaults

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import play.api.libs.json.{JsFalse, JsTrue}
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerBoolean extends AnyFlatSpec with Matchers {

  "Unmarshall JsTrue value" should "return value" in {
    UnmarshallerBoolean.unmarshal(JsTrue) shouldBe true
  }

  "Unmarshall JsFalse value" should "return value" in {
    UnmarshallerBoolean.unmarshal(JsFalse) shouldBe false
  }

  "Unmarshall non JsBool value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerBoolean.unmarshal(null)}
    val expectedErrorMessage = """Illegal input argument to UnmarshallerBoolean: null.
                                 |UnmarshallerBoolean could not unmarshall to boolean value.
                                 |Check if "type" and "var" are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
