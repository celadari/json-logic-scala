package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorRightShiftZeroFillerBitwise extends TestNumeric {


  "Operator RightShiftZeroFillerBitwise Byte >>> Byte" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte >>> yByte.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Byte >>> Short" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte >>> yShort.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Byte >>> Int" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt >>> yInt)
  }

  "Operator RightShiftZeroFillerBitwise Byte >>> Long" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte >>> yLong.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Short >>> Byte" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort >>> yByte.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Short >>> Short" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort >>> yShort.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Short >>> Integer" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort >>> yInt)
  }

  "Operator RightShiftZeroFillerBitwise Short >>> Long" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort >>> yLong.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Int >>> Byte" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt >>> yByte.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Long >>> Byte" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong >>> yByte.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Long >>> Short" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong >>> yShort.toInt)
  }

  "Operator RightShiftZeroFillerBitwise Long >>> Int" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong >>> yInt)
  }

  "Operator RightShiftZeroFillerBitwise Long >>> Long" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong >>> yLong.toInt)
  }

  "Operator RightShiftZeroFillerBitwise different types" should "return value" in {
    val tree = new ComposeLogic(">>>", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte >>> yByte.toInt >>> xLong.toInt >>> yInt >>> xShort.toInt >>> xShort.toInt)
  }

}
