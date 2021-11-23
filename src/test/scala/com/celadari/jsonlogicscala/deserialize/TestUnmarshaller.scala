// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.celadari.jsonlogicscala.deserialize.impl.{UnmarshallerDoubleImpl, UnmarshallerStringImpl}


class TestUnmarshaller extends AnyFlatSpec with Matchers {

  "Compare two instances of UnmarshallerDoubleImpl" should "return true" in {
    val unmarshaller1 = new UnmarshallerDoubleImpl(0)
    val unmarshaller2 = new UnmarshallerDoubleImpl(0)
    unmarshaller1 shouldBe unmarshaller2
  }

  "Compare instance of UnmarshallerDoubleImpl and UnmarshallerStringImpl" should "return false" in {
    val unmarshaller1 = new UnmarshallerDoubleImpl(0)
    val unmarshaller2 = new UnmarshallerStringImpl("before", "after")
    unmarshaller1 shouldNot be (unmarshaller2)
  }

  "Compare instance of UnmarshallerDoubleImpl and double" should "return false" in {
    val unmarshaller1 = new UnmarshallerDoubleImpl(0)
    unmarshaller1 shouldNot be (5d)
  }

}
