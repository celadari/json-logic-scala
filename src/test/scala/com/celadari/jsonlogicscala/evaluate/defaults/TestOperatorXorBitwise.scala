package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorXorBitwise extends TestNumeric {


  "Operator XorBitwise Byte ^ Byte" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte ^ yByte)
  }

  "Operator XorBitwise Byte ^ Short" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte ^ yShort)
  }

  "Operator XorBitwise Byte ^ Int" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt ^ yInt)
  }

  "Operator XorBitwise Byte ^ Long" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte ^ yLong)
  }

  "Operator XorBitwise Short ^ Byte" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort ^ yByte)
  }

  "Operator XorBitwise Short ^ Short" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort ^ yShort)
  }

  "Operator XorBitwise Short ^ Integer" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort ^ yInt)
  }

  "Operator XorBitwise Short ^ Long" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort ^ yLong)
  }

  "Operator XorBitwise Int ^ Byte" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt ^ yByte)
  }

  "Operator XorBitwise Int ^ Short" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt ^ yShort)
  }

  "Operator XorBitwise Int ^ Int" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt ^ yInt)
  }

  "Operator XorBitwise Long ^ Byte" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong ^ yByte)
  }

  "Operator XorBitwise Long ^ Short" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong ^ yShort)
  }

  "Operator XorBitwise Long ^ Int" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong ^ yInt)
  }

  "Operator XorBitwise Long ^ Long" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong ^ yLong)
  }

  "Operator XorBitwise different types" should "return value" in {
    val tree = new ComposeLogic("^", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte ^ yByte ^ xLong ^ yInt ^ xShort ^ xShort)
  }

}
