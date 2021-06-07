package com.github.celadari.jsonlogicscala.serialize

import play.api.libs.json.{JsObject, JsValue, Json}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.{INT_CODENAME, STRING_CODENAME}
import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, OptionTypeValue, SimpleTypeValue, TypeValue}
import com.github.celadari.jsonlogicscala.exceptions.{IllegalInputException, InvalidValueLogicException}
import com.github.celadari.jsonlogicscala.TestPrivateMethods
import com.github.celadari.jsonlogicscala.serialize.SerializerConf.DEFAULT_MARSHALLERS
import com.github.celadari.jsonlogicscala.serialize.impl.{MarshallerBooleanImpl2, MarshallerStringImpl2}


class TestSerializer extends TestPrivateMethods {

  private[this] val serializeValueLogic = PrivateMethod[(JsValue, JsValue)](toSymbol("serializeValueLogic"))
  private[this] val serializeArrayOfConditions = PrivateMethod[(JsValue, JsObject)](toSymbol("serializeArrayOfConditions"))
  private[this] val serializeComposeLogic = PrivateMethod[(JsValue, JsObject)](toSymbol("serializeComposeLogic"))
  private[this] val getMarshaller = PrivateMethod[Marshaller](toSymbol("getMarshaller"))

  "Private method serializeValueLogic" should "return serialized ValueLogic" in {
    val valueLogic = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1"))
    val serializer = new Serializer
    val result = serializer invokePrivate serializeValueLogic(valueLogic)
    result shouldBe (Json.parse("""{"var":"data1","type":{"codename":"int"}}"""), Json.parse("""{"data1":45}"""))
  }

  "Private method serializeValueLogic value but no typeCodenameOpt" should "throw an exception" in {
    val valueLogic = ValueLogic(Some(45), None, pathNameOpt = Some("data1"))
    val serializer = new Serializer
    val thrown = the[InvalidValueLogicException] thrownBy {serializer invokePrivate serializeValueLogic(valueLogic)}
    thrown.getMessage shouldBe "ValueLogic with defined value must define a typeCodename as well"
  }

  "Private method serializeValueLogic empty value" should "return serialized ValueLogic" in {
    val valueLogic = ValueLogic(None, None, pathNameOpt = Some("dataNull"))
    val serializer = new Serializer
    val result = serializer invokePrivate serializeValueLogic(valueLogic)
    result shouldBe (Json.parse("""{"var":"dataNull","type":"null"}"""), Json.parse("""{"dataNull":null}"""))
  }

  "Private method serializeArrayOfConditions" should "return serialized conditions" in {
    val valueLogic1 = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1"))
    val valueLogic2 = ValueLogic(Some("I love New York"), Some(SimpleTypeValue(STRING_CODENAME)), pathNameOpt = Some("data2"))
    val valueLogic3 = ValueLogic(None, None, pathNameOpt = Some("dataNull"))
    val valueLogic4 = ValueLogic(None, None, variableNameOpt = Some("acc"), None)
    val composeLogic1 = new ComposeLogic("+", Array(
      ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
      ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2"))
    ))

    val serializer = new Serializer
    val result = serializer invokePrivate serializeArrayOfConditions(Array(valueLogic1, valueLogic2, valueLogic3, valueLogic4, composeLogic1))
    result shouldBe (
      Json.parse("""[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"string"}},""" +
        """{"var":"dataNull","type":"null"},{"var":"acc"},{"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]}]"""),
      Json.parse("""{"data1":45,"data2":65,"dataNull":null}""")
    )
  }

  "Private method serializeComposeLogic" should "return serialized conditions" in {
    val composeLogic = new ComposeLogic("map", Array(
      ValueLogic(Some(Array(43, 78, 2, 0)), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("dataArr")),
      new ComposeLogic("+", Array(
        ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
        ValueLogic(None, None, variableNameOpt = Some(""), None)
      ))
    ))

    val serializer = new Serializer
    val result = serializer invokePrivate serializeComposeLogic(composeLogic)
    result shouldBe (
      Json.parse(
      """{"map":[{"var":"dataArr","type":{"codename":"array","paramType":{"codename":"int"}}},{"+":[{"var":"data1","type":{"codename":"int"}}""" +
        """,{"var":""}]}]}"""
      ),
      Json.parse("""{"dataArr":[43,78,2,0],"data1":45}""")
    )
  }

  "Serialize ValueLogic" should "return value" in {
    val valueLogic = ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data1"))
    val result = Json.stringify(Json.toJson(valueLogic.asInstanceOf[JsonLogicCore]))
    result shouldBe """[{"var":"data1","type":{"codename":"int"}},{"data1":45}]"""
  }

  "Serialize method VariableLogic" should "throw an exception" in {
    val valueLogic = VariableLogic("acc", new ComposeLogic("+", Array()))
    val serializer = new Serializer
    val thrown = the[InvalidValueLogicException] thrownBy {serializer.serialize(valueLogic)}
    thrown.getMessage shouldBe "VariableLogic cannot be serialized. CompositionOperator.ComposeJsonLogicCore output is only used at evaluation."
  }

  "Serialize default ComposeLogic" should "return value" in {
    val tree = new ComposeLogic("+", Array(
      ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data1")),
      ValueLogic(Some(65), Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("data2"))
    )).asInstanceOf[JsonLogicCore]

    val expectedResult = """[{"+":[{"var":"data1","type":{"codename":"int"}},{"var":"data2","type":{"codename":"int"}}]},{"data1":45,"data2":65}]"""
    Json.stringify(Json.toJson(tree)) shouldBe expectedResult
  }

  "Serialize ComposeLogic with map type" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(
        Some(Map("car" -> Map("5p" -> 45),"bicycle" -> Map("4p" -> 89))),
        Some(MapTypeValue(MapTypeValue(SimpleTypeValue(INT_CODENAME)))),
        pathNameOpt = Some("data1")
      ),
      ValueLogic(
        Some(Map("truck" -> Map("8p" -> 53), "bike" -> Map("4p" -> 89))),
        Some(MapTypeValue(MapTypeValue(SimpleTypeValue(INT_CODENAME)))),
        pathNameOpt = Some("data1")
      )
    ))

    val expectedResult = """[{"merge":[{"var":"data1","type":{"codename":"map","paramType":{"codename":"map","paramType":{"codename":"int"}}}},""" +
      """{"var":"data1","type":{"codename":"map","paramType":{"codename":"map","paramType":{"codename":"int"}}}}]},{"data1":{"truck":{"8p":53},""" +
      """"bike":{"4p":89}}}]"""
    Json.stringify(Json.toJson(tree)(ComposeLogic.composeLogicWrites)) shouldBe expectedResult
    Json.stringify(Json.toJson(tree.asInstanceOf[JsonLogicCore])) shouldBe expectedResult
  }

  "Serialize ComposeLogic with option type" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(
        Some(Map("car" -> Map("5p" -> Some(45)),"bicycle" -> Map("4p" -> Some(89)))),
        Some(MapTypeValue(MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))),
        pathNameOpt = Some("data1")
      ),
      ValueLogic(
        Some(Map("truck" -> Map("8p" -> Some(53)), "bike" -> Map("4p" -> Some(89)))),
        Some(MapTypeValue(MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))),
        pathNameOpt = Some("data1")
      )
    ))

    val expectedResult = """[{"merge":[{"var":"data1","type":{"codename":"map","paramType":{"codename":"map","paramType":{"codename":"option",""" +
      """"paramType":{"codename":"int"}}}}},{"var":"data1","type":{"codename":"map","paramType":{"codename":"map","paramType":{"codename":"option",""" +
      """"paramType":{"codename":"int"}}}}}]},{"data1":{"truck":{"8p":53},"bike":{"4p":89}}}]"""
    Json.stringify(Json.toJson(tree)(ComposeLogic.composeLogicWrites)) shouldBe expectedResult
    Json.stringify(Json.toJson(tree.asInstanceOf[JsonLogicCore])) shouldBe expectedResult
  }

  "Serialize with anytype type" should "throw an exception" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(
        Some(Map("car" -> Map("5p" -> 45),"bicycle" -> Map("4p" -> 89))),
        Some(AnyTypeValue), pathNameOpt = Some("data1")
      ),
      ValueLogic(
        Some(Map("truck" -> Map("8p" -> 53), "bike" -> Map("4p" -> 89))),
        Some(MapTypeValue(MapTypeValue(SimpleTypeValue(INT_CODENAME)))),
        pathNameOpt = Some("data1")
      )
    )).asInstanceOf[JsonLogicCore]
    val thrown = the[IllegalInputException] thrownBy {Json.toJson(tree)}
    val expectedResult = "Cannot serialize JsonLogicCore object with type AnyTypeValue. \nAnyTypeValue is used at evaluation only for composition operators"
    thrown.getMessage shouldBe expectedResult
  }

  "Get marshaller type anytype" should "throw an exception" in {
    val serializer = new Serializer
    val thrown = the[IllegalInputException] thrownBy {serializer invokePrivate getMarshaller(AnyTypeValue)}
    val expectedResult = "Cannot serialize JsonLogicCore object with type AnyTypeValue. \nAnyTypeValue is used at evaluation only for composition operators"
    thrown.getMessage shouldBe expectedResult
  }

  "Get marshaller type any" should "throw an exception" in {
    val serializer = new Serializer
    val thrown = the[IllegalInputException] thrownBy {serializer invokePrivate getMarshaller(null: TypeValue)}
    thrown.getMessage shouldBe "Illegal argument type value"
  }

  "Serialize non map type value with MapTypeValue" should "throw an exception" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(45), Some(MapTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1"))
    )).asInstanceOf[JsonLogicCore]
    val thrown = the[IllegalInputException] thrownBy {Json.toJson(tree)}
    thrown.getMessage shouldBe "Illegal input argument to MapType Marshaller: 45.\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct."
  }

  "Serialize non array type value with ArrayTypeValue" should "throw an exception" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(45), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1"))
    )).asInstanceOf[JsonLogicCore]
    val thrown = the[IllegalInputException] thrownBy {Json.toJson(tree)}
    thrown.getMessage shouldBe "Illegal input argument to ArrayType Marshaller: 45.\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct."
  }

  "Serialize non option type value with OptionTypeValue" should "throw an exception" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(45), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))), pathNameOpt = Some("data1"))
    )).asInstanceOf[JsonLogicCore]
    val thrown = the[IllegalInputException] thrownBy {Json.toJson(tree)}
    thrown.getMessage shouldBe "Illegal input argument to OptionType Marshaller: 45.\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct."
  }

  "createConf other path with meta-inf add priority" should "return serialize with boolean and string marshaller from impl" in {
    val conf = SerializerConf.createConf(
      "META-INF/services/json-logic-scala/tests/serializer/normal/meta-inf-priority/",
      marshallersManualAdd = DEFAULT_MARSHALLERS,
      isPriorityToManualAdd = false
    )
    val serializer = new Serializer()(conf)
    val result = serializer invokePrivate PrivateMethod[Map[String, Marshaller]](toSymbol("marshallers"))()
    val expectedResult = DEFAULT_MARSHALLERS ++ Map("boolean" -> MarshallerBooleanImpl2, "string" -> new MarshallerStringImpl2("before", "after"))

    result shouldBe expectedResult
  }

}
