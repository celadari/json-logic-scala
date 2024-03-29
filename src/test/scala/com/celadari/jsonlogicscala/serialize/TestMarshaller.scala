// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.celadari.jsonlogicscala.serialize.impl.{MarshallerDoubleImpl, MarshallerStringImpl}


class TestMarshaller extends AnyFlatSpec with Matchers {

  "Compare two instances of MarshallerDoubleImpl" should "return true" in {
    val marshaller1 = new MarshallerDoubleImpl(0)
    val marshaller2 = new MarshallerDoubleImpl(0)
    marshaller1 shouldBe marshaller2
  }

  "Compare instance of MarshallerDoubleImpl and MarshallerStringImpl" should "return false" in {
    val marshaller1 = new MarshallerDoubleImpl(0)
    val marshaller2 = new MarshallerStringImpl("before", "after")
    marshaller1 shouldNot be (marshaller2)
  }

  "Compare instance of MarshallerDoubleImpl and double" should "return false" in {
    val marshaller1 = new MarshallerDoubleImpl(0)
    marshaller1 shouldNot be (5d)
  }

}
