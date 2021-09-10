package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorLess extends TestNumeric {


  "Operator Less Byte < Byte" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte < yByte)
  }

  "Operator Less Byte < Short" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte < yShort)
  }

  "Operator Less Byte < Int" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yInt)
  }

  "Operator Less Byte < Long" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte < yLong)
  }

  "Operator Less Byte < Float" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte < yFloat)
  }

  "Operator Less Byte < Double" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte < yDouble)
  }

  "Operator Less Short < Byte" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort < yByte)
  }

  "Operator Less Short < Short" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort < yShort)
  }

  "Operator Less Short < Integer" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort < yInt)
  }

  "Operator Less Short < Long" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort < yLong)
  }

  "Operator Less Short < Float" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort < yFloat)
  }

  "Operator Less Short < Double" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort < yDouble)
  }

  "Operator Less Int < Byte" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yByte)
  }

  "Operator Less Int < Short" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yShort)
  }

  "Operator Less Long < Byte" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong < yByte)
  }

  "Operator Less Long < Short" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong < yShort)
  }

  "Operator Less Long < Int" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong < yInt)
  }

  "Operator Less Long < Long" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong < yLong)
  }

  "Operator Less Long < Double" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong < yDouble)
  }

  "Operator Less Float < Byte" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xFloat < yByte)
  }

  "Operator Less Float < Long" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xFloat < yLong)
  }

  "Operator Less Float < Float" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xFloat < yFloat)
  }

  "Operator Less Float < Double" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xFloat < yDouble)
  }

  "Operator Less Double < Byte" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xDouble < yByte)
  }

  "Operator Less Double < Short" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xDouble < yShort)
  }

  "Operator Less Double < Long" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xDouble < yLong)
  }

  "Operator Less Double < Float" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xDouble < yFloat)
  }

  "Operator Less Int < Int" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yInt)
  }

  "Operator Less Int < Long" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yLong)
  }

  "Operator Less Int < Float" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yFloat)
  }

  "Operator Less Int < Double" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt < yDouble)
  }

  "Operator Less Long < Float" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong < yFloat)
  }

  "Operator Less Float < Short" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xFloat < yShort)
  }

  "Operator Less Float < Int" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xFloat < yInt)
  }

  "Operator Less Double < Int" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xDouble < yInt)
  }

  "Operator Less Double < Double" should "return value" in {
    val tree = new ComposeLogic("<", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xDouble < yDouble)
  }

}
