// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsNumber
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


class TestUnmarshallerInt extends AnyFlatSpec with Matchers {

  "Unmarshall JsNumber value" should "return Int value" in {
    UnmarshallerInt.unmarshal(JsNumber(5)) shouldBe 5
  }

  "Unmarshall non JsNumber value" should "throw an exception" in {
    val thrown = the[InvalidJsonParsingException] thrownBy {UnmarshallerInt.unmarshal(null)}
    val expectedErrorMessage = """Illegal input argument to UnmarshallerInt: null.
                                 |UnmarshallerInt could not unmarshall to Int value.
                                 |Check if "type" and "var" are correct.""".stripMargin
    thrown.getMessage shouldBe expectedErrorMessage
  }

}
