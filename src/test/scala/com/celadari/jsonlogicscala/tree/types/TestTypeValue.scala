// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{Json, JsValue}
import com.celadari.jsonlogicscala.exceptions.{IllegalInputException, InvalidJsonParsingException}
import com.celadari.jsonlogicscala.TestPrivateMethods
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME


class TestTypeValue extends TestPrivateMethods {

  private[this] val parseTypeValue = PrivateMethod[TypeValue](toSymbol("parseTypeValue"))
  private[this] val serializeTypeValue = PrivateMethod[JsValue](toSymbol("serializeTypeValue"))

  "Private method parseTypeValue SimpleTypeValue(INT_CODENAME)" should "return deserialized TypeValue" in {
    val jsType = Json.parse("""{"codename":"int"}""")
    val resultExpected = SimpleTypeValue(INT_CODENAME)
    val result = TypeValue invokePrivate parseTypeValue(jsType)
    result shouldBe resultExpected
  }

  "Private method parseTypeValue ArrayTypeValue(SimpleTypeValue(INT_CODENAME))" should "return deserialized TypeValue" in {
    val jsType = Json.parse("""{"codename": "array","paramType":{"codename":"int"}}""")
    val resultExpected = ArrayTypeValue(SimpleTypeValue(INT_CODENAME))
    val result = TypeValue invokePrivate parseTypeValue(jsType)
    result shouldBe resultExpected
  }

  "Private method parseTypeValue MapTypeValue(SimpleTypeValue(INT_CODENAME))" should "return deserialized TypeValue" in {
    val jsType = Json.parse("""{"codename": "map","paramType":{"codename":"int"}}""")
    val resultExpected = MapTypeValue(SimpleTypeValue(INT_CODENAME))
    val result = TypeValue invokePrivate parseTypeValue(jsType)
    result shouldBe resultExpected
  }

  "Private method parseTypeValue OptionTypeValue(SimpleTypeValue(INT_CODENAME))" should "return deserialized TypeValue" in {
    val jsType = Json.parse("""{"codename": "option","paramType":{"codename":"int"}}""")
    val resultExpected = OptionTypeValue(SimpleTypeValue(INT_CODENAME))
    val result = TypeValue invokePrivate parseTypeValue(jsType)
    result shouldBe resultExpected
  }

  "Private method parseTypeValue MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME)))" should "return deserialized TypeValue" in {
    val jsType = Json.parse("""{"codename": "map","paramType":{"codename": "option","paramType":{"codename":"int"}}}""")
    val resultExpected = MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME)))
    val result = TypeValue invokePrivate parseTypeValue(jsType)
    result shouldBe resultExpected
  }

  "Private method parseTypeValue AnyTypeValue" should "throw an exception" in {
    val jsType = Json.parse("""{"codename":"anytype","paramType":{}}""")
    val thrown = the[InvalidJsonParsingException] thrownBy {TypeValue invokePrivate parseTypeValue(jsType)}
    thrown.getMessage shouldBe "Illegal 'codename': 'anytype' in parameter type. SimpleTypeValue cannot have 'paramType' key"
  }

  "Private method parseTypeValue other than 'codename' and 'paramType' key" should "throw an exception" in {
    val jsType = Json.parse("""{"codename":"int","param":{}}""")
    val thrown = the[InvalidJsonParsingException] thrownBy {TypeValue invokePrivate parseTypeValue(jsType)}
    thrown.getMessage shouldBe """TypeValue cannot have other keys than 'codename' and 'paramType': {"codename":"int","param":{}}"""
  }

  "Parse json into SimpleTypeValue(INT_CODENAME)" should "return deserialized TypeValue" in {
    val jsType = Json.parse("""{"codename":"int"}""")
    val result = jsType.as[TypeValue]
    result shouldBe SimpleTypeValue(INT_CODENAME)
  }

  "Parse invalid json into TypeValue" should "throw an exception" in {
    val jsType = Json.parse("""{"codename":"int","param":{}}""")
    an[play.api.libs.json.JsResultException] shouldBe thrownBy{jsType.as[TypeValue]}
  }

  "Private method serializeTypeValue SimpleTypeValue(INT_CODENAME)" should "return deserialized TypeValue" in {
    val typeValue = SimpleTypeValue(INT_CODENAME)
    val resultExpected = Json.parse("""{"codename":"int"}""")
    val result = TypeValue invokePrivate serializeTypeValue(typeValue)
    result shouldBe resultExpected
  }

  "Private method serializeTypeValue ArrayTypeValue(SimpleTypeValue(INT_CODENAME))" should "return deserialized TypeValue" in {
    val typeValue = ArrayTypeValue(SimpleTypeValue(INT_CODENAME))
    val resultExpected = Json.parse("""{"codename": "array","paramType":{"codename":"int"}}""")
    val result = TypeValue invokePrivate serializeTypeValue(typeValue)
    result shouldBe resultExpected
  }

  "Private method serializeTypeValue MapTypeValue(SimpleTypeValue(INT_CODENAME))" should "return deserialized TypeValue" in {
    val typeValue = MapTypeValue(SimpleTypeValue(INT_CODENAME))
    val resultExpected = Json.parse("""{"codename": "map","paramType":{"codename":"int"}}""")
    val result = TypeValue invokePrivate serializeTypeValue(typeValue)
    result shouldBe resultExpected
  }

  "Private method serializeTypeValue OptionTypeValue(SimpleTypeValue(INT_CODENAME))" should "return deserialized TypeValue" in {
    val typeValue = OptionTypeValue(SimpleTypeValue(INT_CODENAME))
    val resultExpected = Json.parse("""{"codename": "option","paramType":{"codename":"int"}}""")
    val result = TypeValue invokePrivate serializeTypeValue(typeValue)
    result shouldBe resultExpected
  }

  "Private method serializeTypeValue MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME)))" should "return deserialized TypeValue" in {
    val typeValue = MapTypeValue(OptionTypeValue(SimpleTypeValue(INT_CODENAME)))
    val resultExpected = Json.parse("""{"codename": "map","paramType":{"codename": "option","paramType":{"codename":"int"}}}""")
    val result = TypeValue invokePrivate serializeTypeValue(typeValue)
    result shouldBe resultExpected
  }

  "Private method serializeTypeValue AnyTypeValue" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {TypeValue invokePrivate serializeTypeValue(AnyTypeValue)}
    thrown.getMessage should fullyMatch regex """'com\.celadari\.jsonlogicscala\.tree\.types\.AnyTypeValue\$@[a-zA-Z0-9]+' type cannot be serialized"""
  }

  "Write ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) into json" should "return deserialized TypeValue" in {
    val typeValue = ArrayTypeValue(SimpleTypeValue(INT_CODENAME))
    val resultExpected = Json.parse("""{"codename": "array","paramType":{"codename":"int"}}""")
    val result = Json.toJson(typeValue)(ArrayTypeValue.writeArrayTypeValue)
    result shouldBe resultExpected
  }

  "Write MapTypeValue(SimpleTypeValue(INT_CODENAME)) into json" should "return deserialized TypeValue" in {
    val typeValue = MapTypeValue(SimpleTypeValue(INT_CODENAME))
    val resultExpected = Json.parse("""{"codename": "map","paramType":{"codename":"int"}}""")
    val result = Json.toJson(typeValue)(MapTypeValue.writeMapTypeValue)
    result shouldBe resultExpected
  }

  "Write OptionTypeValue(SimpleTypeValue(INT_CODENAME)) into json" should "return deserialized TypeValue" in {
    val typeValue = OptionTypeValue(SimpleTypeValue(INT_CODENAME))
    val resultExpected = Json.parse("""{"codename": "option","paramType":{"codename":"int"}}""")
    val result = Json.toJson(typeValue)(OptionTypeValue.writeOptionTypeValue)
    result shouldBe resultExpected
  }

  "Write SimpleTypeValue(INT_CODENAME) into json" should "return deserialized TypeValue" in {
    val typeValue = SimpleTypeValue(INT_CODENAME)
    val resultExpected = Json.parse("""{"codename":"int"}""")
    val result = Json.toJson(typeValue)(SimpleTypeValue.writeSimpleTypeValue)
    result shouldBe resultExpected
  }

  "Write AnyTypeValue" should "throw an exception" in {
    val thrown = the[IllegalInputException] thrownBy {Json.toJson(AnyTypeValue)(AnyTypeValue.writeArrayTypeValue)}
    thrown.getMessage should fullyMatch regex """'com\.celadari\.jsonlogicscala\.tree\.types\.AnyTypeValue\$@[a-zA-Z0-9]+' type cannot be serialized"""
  }

}
