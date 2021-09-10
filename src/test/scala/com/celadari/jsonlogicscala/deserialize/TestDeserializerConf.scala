package com.celadari.jsonlogicscala.deserialize

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.celadari.jsonlogicscala.deserialize
import com.celadari.jsonlogicscala.exceptions.ConfigurationException
import com.celadari.jsonlogicscala.deserialize.impl._


class TestDeserializerConf extends AnyFlatSpec with Matchers {

  "createConf default path" should "return conf" in {
    val result = DeserializerConf.createConf()
    val expectedResult = deserialize.DeserializerConf(Map(
      "boolean" -> UnmarshallerBooleanImpl,
      "string" -> new UnmarshallerStringImpl("before", "after"),
      "int" -> UnmarshallerIntImpl,
      "double" -> new UnmarshallerDoubleImpl(0)
    ), DeserializerConf.DEFAULT_UNMARSHALLERS
    )

    result shouldBe expectedResult
  }

  "createConf other path with manual add priority" should "return conf" in {
    val result = DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/normal/manual-add-priority/")
    val expectedResult = deserialize.DeserializerConf(Map(
      "boolean" -> UnmarshallerBooleanImpl2,
      "string" -> new UnmarshallerStringImpl2("before", "after")
    ), DeserializerConf.DEFAULT_UNMARSHALLERS
    )

    result shouldBe expectedResult
  }

  "createConf other path with meta-inf add priority" should "return conf" in {
    val result = DeserializerConf.createConf(
      "META-INF/services/json-logic-scala/tests/deserializer/normal/meta-inf-priority/",
      unmarshallersManualAdd = DeserializerConf.DEFAULT_UNMARSHALLERS,
      isPriorityToManualAdd = false
    )
    val expectedResult = deserialize.DeserializerConf(Map(
      "boolean" -> UnmarshallerBooleanImpl2,
      "string" -> new UnmarshallerStringImpl2("before", "after")
    ),
      DeserializerConf.DEFAULT_UNMARSHALLERS,
      isPriorityToManualAdd = false
    )

    result shouldBe expectedResult
  }

  "createConf non unmarshaller object" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/cast-exception-singleton/")
    }
    val expectedMessage =
      """Found object is not a 'com.celadari.jsonlogicscala.deserialize.Unmarshaller' instance:
        |'com.celadari.jsonlogicscala.serialize.impl.MarshallerIntImpl$'""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf non singleton with singleton true in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/non-singleton-exception-singleton-set-to-true/")
    }
    val expectedMessage =
      """No singleton object found for: 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerStringImpl'
        |Check if 'className' 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerStringImpl' is correct and if """.stripMargin +
        """'singleton' property in 'string' property file is correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf non unmarshaller class" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/cast-exception-class/")
    }
    val expectedMessage =
      """Found class is not a 'com.celadari.jsonlogicscala.deserialize.Unmarshaller' instance:
        |'com.celadari.jsonlogicscala.serialize.impl.MarshallerDoubleImpl'""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf singleton with singleton false in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/singleton-exception-singleton-set-to-false/")
    }
    val expectedMessage =
      """No class found for: 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerIntImpl'
        |Check if 'className' 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerIntImpl' is correct and if 'singleton'""".stripMargin +
        """ property in 'int' property file is correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf className not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/classname-not-defined-exception-class/")
    }
    val expectedMessage = "Property file 'double' must define key 'className'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf codename not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/codename-not-defined-exception-class/")
    }
    val expectedMessage = "Property file 'double' must define key 'codename'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf wrong constructor argument names in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf(
        "META-INF/services/json-logic-scala/tests/deserializer/exceptions/wrong-constructor-argument-names-definition-exception-class/"
      )
    }
    val expectedMessage =
      """Field error, check that no field in 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerStringImpl2' is missing """ +
        """in 'string' property file.
          |Check that no property in 'string' file is not undefined in 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerStringImpl2' class.
          |Check if 'com.celadari.jsonlogicscala.deserialize.impl.UnmarshallerStringImpl2' class constructor requires arguments or if""".stripMargin +
          """ argument names defined in 'string' property file are correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf wrong property type in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      DeserializerConf.createConf("META-INF/services/json-logic-scala/tests/deserializer/exceptions/wrong-property-type-exception-class/")
    }
    val expectedMessage = "Property 'singleton' in property file 'string' is not a valid boolean parameter"
    thrown.getMessage shouldBe expectedMessage
  }
}
