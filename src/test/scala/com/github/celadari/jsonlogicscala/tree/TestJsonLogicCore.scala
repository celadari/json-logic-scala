package com.github.celadari.jsonlogicscala.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.evaluate.CompositionOperator.ComposeJsonLogicCore
import com.github.celadari.jsonlogicscala.evaluate.defaults.{TestArray, TestNumeric}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.{INT_CODENAME, STRING_CODENAME}
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue


class TestJsonLogicCore extends AnyFlatSpec with Matchers with TestNumeric with TestArray {

  "Method treeString" should "return string tree representation" in {
    val composeLogic = new ComposeLogic("+", Array(
      new ComposeLogic("*", Array(
        ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data1")),
        ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data2"))
      )),
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data3"))
    ))
    val expectedString = """+
                           |├──*
                           |│  ├──{ValueLogic Data 'data1': 5}
                           |│  └──{ValueLogic Data 'data2': 47}
                           |└──{ValueLogic Data 'data3': 5}""".stripMargin

    composeLogic.treeString shouldBe expectedString
  }

  "Method treeString on composed tree" should "return string tree representation" in {
    val tree = new ComposeLogic("all", Array(
      ValueLogic(Some(arrString), Some(arrStringType), None, Some("data1")),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some("Singing in the rain"), Some(SimpleTypeValue(STRING_CODENAME)), None, Some("data2"))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType), None, Some("data3"))
    ))

    val composedTree = new ComposeJsonLogicCore(tree).evaluate().asInstanceOf[ComposeLogic]
    val expectedString = """all
                           |├──\{ValueLogic Data 'data1': \[Ljava.lang.String;@[a-zA-Z0-9]+\}
                           |├──in
                           |│  ├──\{VariableLogic '' of 'com.github.celadari.jsonlogicscala.tree.ComposeLogic@[a-zA-Z0-9]+' compose operator\}
                           |│  └──\{ValueLogic Data 'data2': Singing in the rain\}
                           |└──\{ValueLogic Data 'data3': \[Ljava.lang.Integer;@[a-zA-Z0-9]+\}""".stripMargin

    composedTree.treeString should fullyMatch regex expectedString
  }

  "Method treeString on non-composed tree yet" should "return string tree representation" in {
    val tree = new ComposeLogic("all", Array(
      ValueLogic(Some(arrString), Some(arrStringType), None, Some("data1")),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some("Singing in the rain"), Some(SimpleTypeValue(STRING_CODENAME)), None, Some("data2"))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType), None, Some("data3"))
    ))

    val expectedString = """all
                           |├──\{ValueLogic Data 'data1': \[Ljava.lang.String;@[a-zA-Z0-9]+\}
                           |├──in
                           |│  ├──\{ValueLogic Variable ''\}
                           |│  └──\{ValueLogic Data 'data2': Singing in the rain\}
                           |└──\{ValueLogic Data 'data3': \[Ljava.lang.Integer;@[a-zA-Z0-9]+\}""".stripMargin

    tree.treeString should fullyMatch regex expectedString
  }

  "Method treeString ValueLogic Data" should "return string tree representation" in {
    val valueLogic = ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data1"))

    val expectedString = """{ValueLogic Data 'data1': 5}"""

    valueLogic.treeString shouldBe expectedString
  }

  "Method treeString ValueLogic Variable" should "return string tree representation" in {
    val valueLogic = ValueLogic(None, None, Some(""), None)

    val expectedString = """{ValueLogic Variable ''}"""

    valueLogic.treeString shouldBe expectedString
  }

}
