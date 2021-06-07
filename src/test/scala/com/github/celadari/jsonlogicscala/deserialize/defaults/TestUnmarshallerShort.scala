package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.JsNumber
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException

class TestUnmarshallerShort extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Short value" in {
    UnmarshallerShort.unmarshal(JsNumber(5.toShort)) shouldBe 5.toShort
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerShort.unmarshal(null)}
    val expectedErrorMessage = """Illegal input argument to UnmarshallerShort: null.
                                 |UnmarshallerShort could not unmarshall to Short value.
                                 |Check if "type" and "var" are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
