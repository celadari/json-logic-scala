package com.celadari.jsonlogicscala.deserialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsNull, JsNumber}
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


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
