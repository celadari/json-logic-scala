package com.github.celadari.jsonlogicscala.serialize

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.serialize.impl._
import com.github.celadari.jsonlogicscala.exceptions.ConfigurationException
import com.github.celadari.jsonlogicscala.serialize.SerializerConf.DEFAULT_MARSHALLERS


class TestSerializerConf extends AnyFlatSpec with Matchers {

  "createConf default path" should "return conf" in {
    val result = SerializerConf.createConf()
    val expectedResult = SerializerConf(Map(
      "boolean" -> MarshallerBooleanImpl,
      "string" -> new MarshallerStringImpl("before", "after"),
      "int" -> MarshallerIntImpl,
      "double" -> new MarshallerDoubleImpl(0)
    ), SerializerConf.DEFAULT_MARSHALLERS
    )

    result shouldBe expectedResult
  }

  "createConf other path with manual add priority" should "return conf" in {
    val result = SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/normal/manual-add-priority/")
    val expectedResult = SerializerConf(Map(
      "boolean" -> MarshallerBooleanImpl2,
      "string" -> new MarshallerStringImpl2("before", "after")
    ), SerializerConf.DEFAULT_MARSHALLERS
    )

    result shouldBe expectedResult
  }

  "createConf other path with meta-inf add priority" should "return conf" in {
    val result = SerializerConf.createConf(
      "META-INF/services/json-logic-scala/tests/serializer/normal/meta-inf-priority/",
      marshallersManualAdd = DEFAULT_MARSHALLERS,
      isPriorityToManualAdd = false
    )
    val expectedResult = SerializerConf(Map(
      "boolean" -> MarshallerBooleanImpl2,
      "string" -> new MarshallerStringImpl2("before", "after")
    ),
      SerializerConf.DEFAULT_MARSHALLERS,
      isPriorityToManualAdd = false
    )

    result shouldBe expectedResult
  }

  "createConf non marshaller object" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/cast-exception-singleton/")
    }
    val expectedMessage =
      """Found object is not a 'com.github.celadari.jsonlogicscala.serialize.Marshaller' instance:
        |'com.github.celadari.jsonlogicscala.deserialize.impl.UnmarshallerIntImpl$'""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf non singleton with singleton true in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/non-singleton-exception-singleton-set-to-true/")
    }
    val expectedMessage =
      """No singleton object found for: 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerStringImpl'
        |Check if 'className' 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerStringImpl' is correct and if 'singleton' property""".stripMargin +
        """ in 'string' property file is correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf non marshaller class" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/cast-exception-class/")
    }
    val expectedMessage =
      """Found class is not a 'com.github.celadari.jsonlogicscala.serialize.Marshaller' instance:
        |'com.github.celadari.jsonlogicscala.deserialize.impl.UnmarshallerDoubleImpl'""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf singleton with singleton false in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/singleton-exception-singleton-set-to-false/")
    }
    val expectedMessage =
      """No class found for: 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerIntImpl'
        |Check if 'className' 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerIntImpl' is correct and if 'singleton' property""".stripMargin +
        """ in 'int' property file is correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf className not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/classname-not-defined-exception-class/")
    }
    val expectedMessage = "Property file 'double' must define key 'className'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf codename not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/codename-not-defined-exception-class/")
    }
    val expectedMessage = "Property file 'double' must define key 'codename'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf wrong constructor argument names in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf(
        "META-INF/services/json-logic-scala/tests/serializer/exceptions/wrong-constructor-argument-names-definition-exception-class/"
      )
    }
    val expectedMessage =
      """Field error, check that no field in 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerStringImpl2' is missing in 'string' property file.
        |Check that no property in 'string' file is not undefined in 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerStringImpl2' class.
        |Check if 'com.github.celadari.jsonlogicscala.serialize.impl.MarshallerStringImpl2' class constructor requires arguments or if """.stripMargin +
        """argument names defined in 'string' property file are correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf wrong property type in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {
      SerializerConf.createConf("META-INF/services/json-logic-scala/tests/serializer/exceptions/wrong-property-type-exception-class/")
    }
    val expectedMessage = "Property 'singleton' in property file 'string' is not a valid boolean parameter"
    thrown.getMessage shouldBe expectedMessage
  }

}
