package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNull, JsNumber}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerNull extends AnyFlatSpec with Matchers {

  "Unmarshall JsNUll value" should "return null value" in {
    UnmarshallerNull.unmarshal(JsNull) shouldBe null.asInstanceOf[Any]
  }

  "Unmarshall non JsNull value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerNull.unmarshal(JsNumber(5))}
    val expectedErrorMessage = """Illegal input argument to UnmarshallerNull: 5.
                                 |UnmarshallerNull can only be applied to null value.
                                 |Check if "type" and "var" are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
