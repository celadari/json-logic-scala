package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorLeftShiftBitwise extends TestNumeric {

  "Operator LeftShiftBitwise Byte << Byte" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte << yByte.toInt)
  }

  "Operator LeftShiftBitwise Byte << Short" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte << yShort.toInt)
  }

  "Operator LeftShiftBitwise Byte << Int" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt << yInt)
  }

  "Operator LeftShiftBitwise Byte << Long" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte << yLong.toInt)
  }

  "Operator LeftShiftBitwise Short << Byte" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort << yByte.toInt)
  }

  "Operator LeftShiftBitwise Short << Short" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort << yShort.toInt)
  }

  "Operator LeftShiftBitwise Int << Byte" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt << yByte.toInt)
  }

  "Operator LeftShiftBitwise Long << Byte" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong << yByte.toInt)
  }

  "Operator LeftShiftBitwise Long << Short" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong << yShort.toInt)
  }

  "Operator LeftShiftBitwise Short << Integer" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort << yInt)
  }

  "Operator LeftShiftBitwise Short << Long" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort << yLong.toInt)
  }

  "Operator LeftShiftBitwise Long << Int" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong << yInt)
  }

  "Operator LeftShiftBitwise Long << Long" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong << yLong)
  }

  "Operator LeftShiftBitwise different types" should "return value" in {
    val tree = new ComposeLogic("<<", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte << yByte.toInt << xLong.toInt << yInt << xShort.toInt << xShort.toInt)
  }

}
