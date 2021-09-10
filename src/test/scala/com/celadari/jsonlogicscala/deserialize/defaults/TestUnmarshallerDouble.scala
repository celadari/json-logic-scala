package com.celadari.jsonlogicscala.deserialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsNull, JsNumber}
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerDouble extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Double value" in {
    UnmarshallerDouble.unmarshal(JsNumber(5d)) shouldBe 5d
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerDouble.unmarshal(JsNull)}
    val expectedErrorMessage = """Illegal input argument to UnmarshallerDouble: null.
                                 |UnmarshallerDouble could not unmarshall to Double value.
                                 |Check if "type" and "var" are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
