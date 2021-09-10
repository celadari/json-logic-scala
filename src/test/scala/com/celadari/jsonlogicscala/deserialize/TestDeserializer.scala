package com.celadari.jsonlogicscala.deserialize

import play.api.libs.json.{JsObject, Json}
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException
import com.celadari.jsonlogicscala.JsonLogicCoreMatcher.BeEqualJsonLogicCore
import com.celadari.jsonlogicscala.deserialize.DeserializerConf.DEFAULT_UNMARSHALLERS
import com.celadari.jsonlogicscala.TestPrivateMethods
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}
import com.celadari.jsonlogicscala.tree.types._
import com.celadari.jsonlogicscala.deserialize.impl.{UnmarshallerBooleanImpl2, UnmarshallerStringImpl2}


class TestDeserializer extends TestPrivateMethods {

  private[this] val deserializeValueLogic = PrivateMethod[ValueLogic[_]](toSymbol("deserializeValueLogic"))
  private[this] val deserializeArrayOfConditions = PrivateMethod[Array[JsonLogicCore]](toSymbol("deserializeArrayOfConditions"))
  private[this] val deserializeComposeLogic = PrivateMethod[ComposeLogic](toSymbol("deserializeComposeLogic"))
  private[this] val getUnmarshaller = PrivateMethod[Unmarshaller](toSymbol("getUnmarshaller"))

  "Private method deserializeValueLogic" should "return deserialized ValueLogic" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data1","type":{"codename":"int"}}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val resultExpected = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1"))
    val result = deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)
    result shouldBe resultExpected
  }

  "Private method deserializeValueLogic type but undefined path data" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data2","type":{"codename":"int"}}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "Error while parsing ValueLogic of type value: \"var\" data2 is undefined"
  }

  "Private method deserializeValueLogic defined path data but no type" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data1"}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "Error while parsing ValueLogic of type variable: \"var\" must not be a key on data dictionary.\nActual: \"data1\""
  }

  "Private method deserializeValueLogic type null" should "return deserialized ValueLogic" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data1","type":{"codename":"null"}}"""), Json.parse("""{"data1":null}"""))
    val deserializer = new Deserializer
    val resultExpected = ValueLogic(None, Some(SimpleTypeValue(NULL_CODENAME)), pathNameOpt = Some("data1"))
    val result = deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)
    result shouldBe resultExpected
  }

  "Private method deserializeValueLogic wrong type json format" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("""{"var":"data2","type":"int"}"""), Json.parse("""{"data1":45}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeValueLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "Invalid typevalue json format: \"int\""
  }

  "Private method deserializeComposeLogic composeLogic json more than one field" should "throw an exception" in {
    val (jsLogic, jsData) = (
      Json.parse("""{"*":[{"var":"data1","type":{"codename":"int"}}, {"var":"data1","type":{"codename":"int"}}],
        |"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]}""".stripMargin),
      Json.parse("""{"data1":null}""")
    )
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeComposeLogic(jsLogic, jsData)}
    val expectedMessage = """ComposeLogic cannot have more than one operator field.
                            |Current operators: [*, +]
                            |Invalid ComposeLogic json: {"*":[{"var":"data1","type":{"codename":"int"}},{"var":"data1",""".stripMargin +
                            """"type":{"codename":"int"}}],"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]}"""
    thrown.getMessage shouldBe expectedMessage
  }

  "Private method deserializeComposeLogic composeLogic empty" should "throw an exception" in {
    val (jsLogic, jsData) = (Json.parse("{}"), Json.parse("""{"data1":null}"""))
    val deserializer = new Deserializer
    val thrown = the[InvalidJsonParsingException] thrownBy {deserializer invokePrivate deserializeComposeLogic(jsLogic, jsData)}
    thrown.getMessage shouldBe "ComposeLogic cannot be empty"
  }

  "Private method deserializeArrayOfConditions" should "return deserialized conditions" in {
    val (jsLogic, jsData) = (
      Json.parse("""[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]"""),
      Json.parse("""{"data1":45,"data2":65}""")
    )
    val deserializer = new Deserializer
    val resultExpected = Array(
      ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
      ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2"))
    )
    val result = deserializer invokePrivate deserializeArrayOfConditions(jsLogic, jsData)
    result shouldBe resultExpected
  }

  "Deserialize ComposeLogic map operator" should "return value" in {
    val (jsLogic, jsData) = (
      Json.parse("""{"map":[{"var":"data1","type":{"codename":"array",
        |"paramType":{"codename":"int"}}},{"+":[{"+":[{"var":"data3","type":{"codename":"long"}},
        |{"var":"data5","type":{"codename":"double"}},{"var":"data4","type":{"codename":"float"}}]},
        |{"var":"data2","type":{"codename":"int"}},{"var":""}]}]}""".stripMargin),
      Json.parse("""{"data1":[4,5,6,9],"data3":54,"data5":53,"data4":74,"data2":65}""")
    )
    val deserializer = new Deserializer
    val resultExpected = new ComposeLogic("map", Array(
      ValueLogic(Some(Array(4, 5, 6, 9)), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1")),
      new ComposeLogic("+", Array(
        new ComposeLogic("+", Array(
          ValueLogic(Some(54L), Some(SimpleTypeValue(LONG_CODENAME)), pathNameOpt = Some("data3")),
          ValueLogic(Some(53d), Some(SimpleTypeValue(DOUBLE_CODENAME)), pathNameOpt = Some("data5")),
          ValueLogic(Some(74f), Some(SimpleTypeValue(FLOAT_CODENAME)), pathNameOpt = Some("data4"))
        )),
        ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2")),
        ValueLogic(None, None, variableNameOpt = Some(""), None)
      ))
    )).asInstanceOf[JsonLogicCore]

    val result = deserializer.deserialize(jsLogic.asInstanceOf[JsObject], jsData.asInstanceOf[JsObject])
    result should BeEqualJsonLogicCore (resultExpected)
  }

  "Deserialize ComposeLogic valueLogic value Map(String -> Int)" should "return value" in {
    val (jsLogic, jsData) = (Json.parse(
      """{"in":[{"var":"data2","type":{"codename":"string"}},{"var":"data1",
        |"type":{"codename":"map","paramType":{"codename":"int"}}}]}""".stripMargin), Json.parse("""{"data2":"car","data1":{"car":4,"truck":67}}"""))
    val deserializer = new Deserializer
    val resultExpected = new ComposeLogic("in", Array(
      ValueLogic(Some("car"), Some(SimpleTypeValue(STRING_CODENAME)), pathNameOpt = Some("data2")),
      ValueLogic(Some(Map("car" -> 4, "truck" -> 67)), Some(MapTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1"))
    )).asInstanceOf[JsonLogicCore]

    val result = deserializer.deserialize(jsLogic.asInstanceOf[JsObject], jsData.asInstanceOf[JsObject])
    result should BeEqualJsonLogicCore (resultExpected)
  }

  "Private method getUnmarshaller AnyTypeValue" should "throw an exception" in {
    val deserializer = new Deserializer
    val thrown = the[IllegalArgumentException] thrownBy {deserializer invokePrivate getUnmarshaller(AnyTypeValue)}
    thrown.getMessage shouldBe "Cannot serialize type AnyTypeValue.\nAnyTypeValue is for sole use at evaluation for composition operators"
  }

  "Deserialize ComposeLogic valueLogic value Option(Int)" should "return value" in {
    val (jsLogic, jsData) = (
      Json.parse("""{"get_or_default_boolean":[{"var":"data2","type":{"codename":"option","paramType":{"codename":"string"}}}]}"""),
      Json.parse("""{"data2":null}""")
    )
    val deserializer = new Deserializer
    val resultExpected = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(None.asInstanceOf[Option[String]]), Some(OptionTypeValue(SimpleTypeValue(STRING_CODENAME))), pathNameOpt = Some("data2"))
    )).asInstanceOf[JsonLogicCore]

    val result = deserializer.deserialize(jsLogic.asInstanceOf[JsObject], jsData.asInstanceOf[JsObject])
    result should BeEqualJsonLogicCore (resultExpected)
  }

  "Deserialize ComposeLogic valueLogic value Option(Map(String -> Option(Int))" should "return value" in {
    val (jsLogic, jsData) = (
      Json.parse(
        """{"get_or_default_boolean":[{"var":"data2","type":{"codename":"option","paramType":{"codename":"map",
          |"paramType":{"codename":"option","paramType":{"codename":"int"}}}}}]}""".stripMargin),
      Json.parse("""{"data2":{"car":null,"truck":45}}""")
    )
    val deserializer = new Deserializer
    val resultExpected = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(
        Some(Some(Map("car" -> None, "truck" -> Some(45)))),
        Some(OptionTypeValue(MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))),
        pathNameOpt = Some("data2")
      )
    )).asInstanceOf[JsonLogicCore]

    val result = deserializer.deserialize(jsLogic.asInstanceOf[JsObject], jsData.asInstanceOf[JsObject])
    result should BeEqualJsonLogicCore (resultExpected)
  }

  "createConf other path with meta-inf add priority" should "return deserialize with boolean and string unmarshaller from impl" in {
    val conf = DeserializerConf.createConf(
      "META-INF/services/json-logic-scala/tests/deserializer/normal/meta-inf-priority/",
      unmarshallersManualAdd = DEFAULT_UNMARSHALLERS,
      isPriorityToManualAdd = false
    )
    val deserializer = new Deserializer()(conf)
    val result = deserializer invokePrivate PrivateMethod[Map[String, Unmarshaller]](toSymbol("unmarshallers"))()
    val expectedResult = DEFAULT_UNMARSHALLERS ++ Map("boolean" -> UnmarshallerBooleanImpl2, "string" -> new UnmarshallerStringImpl2("before", "after"))

    result shouldBe expectedResult
  }

  "Deserialize conventional play api ComposeLogic map operator" should "return value" in {
    val jsComposeLogic = Json.parse(
      """[{"map":[{"var":"data1","type":{"codename":"array",
        |"paramType":{"codename":"int"}}},{"+":[{"+":[{"var":"data3","type":{"codename":"long"}},
        |{"var":"data5","type":{"codename":"double"}},{"var":"data4","type":{"codename":"float"}}]},
        |{"var":"data2","type":{"codename":"int"}},{"var":""}]}]},
        |{"data1":[4,5,6,9],"data3":54,"data5":53,"data4":74,"data2":65}]""".stripMargin)
    val resultExpected = new ComposeLogic("map", Array(
      ValueLogic(Some(Array(4, 5, 6, 9)), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1")),
      new ComposeLogic("+", Array(
        new ComposeLogic("+", Array(
          ValueLogic(Some(54L), Some(SimpleTypeValue(LONG_CODENAME)), pathNameOpt = Some("data3")),
          ValueLogic(Some(53d), Some(SimpleTypeValue(DOUBLE_CODENAME)), pathNameOpt = Some("data5")),
          ValueLogic(Some(74f), Some(SimpleTypeValue(FLOAT_CODENAME)), pathNameOpt = Some("data4"))
        )),
        ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2")),
        ValueLogic(None, None, variableNameOpt = Some(""), None)
      ))
    ))

    val resultJsonLogicCore = jsComposeLogic.as[JsonLogicCore]
    val resultComposeLogic = jsComposeLogic.as[ComposeLogic]
    resultJsonLogicCore should BeEqualJsonLogicCore (resultExpected)
    resultComposeLogic should BeEqualJsonLogicCore (resultExpected)
  }

  "Deserialize conventional play api ValueLogic" should "return value" in {
    val jsValueLogic = Json.parse(
      """[{"var":"data2","type":{"codename":"int"}},
          |{"data2":65}]""".stripMargin)
    val resultExpected = ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data2"))

    val resultJsonLogicCore = jsValueLogic.as[JsonLogicCore]
    val resultValueLogic = jsValueLogic.as[ValueLogic[_]]
    resultJsonLogicCore should BeEqualJsonLogicCore (resultExpected)
    resultValueLogic should BeEqualJsonLogicCore (resultExpected)
  }
}
