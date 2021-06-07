package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.JsNumber
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerFloat extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Float value" in {
    UnmarshallerFloat.unmarshal(JsNumber(5f)) shouldBe 5f
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerFloat.unmarshal(null)}
    val expectedErrorMessage = """Illegal input argument to UnmarshallerFloat: null.
                                 |UnmarshallerFloat could not unmarshall to Float value.
                                 |Check if "type" and "var" are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
