package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorOrBitwise extends TestNumeric {

  "Operator OrBitwise Byte | Byte" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yByte)
  }

  "Operator OrBitwise Byte | Short" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yShort)
  }

  "Operator OrBitwise Byte | Int" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt | yInt)
  }

  "Operator OrBitwise Byte | Long" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yLong)
  }

  "Operator OrBitwise Short | Byte" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort | yByte)
  }

  "Operator OrBitwise Short | Short" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort | yShort)
  }

  "Operator OrBitwise Short | Integer" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort | yInt)
  }

  "Operator OrBitwise Short | Long" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xShort | yLong)
  }

  "Operator OrBitwise Int | Byte" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt | yByte)
  }

  "Operator OrBitwise Int | Short" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt | yShort)
  }

  "Operator OrBitwise Int | Int" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt | yInt)
  }

  "Operator OrBitwise Long | Byte" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong | yByte)
  }

  "Operator OrBitwise Long | Short" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong | yShort)
  }

  "Operator OrBitwise Long | Int" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong | yInt)
  }

  "Operator OrBitwise Long | Long" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xLong | yLong)
  }

  "Operator OrBitwise different types" should "return value" in {
    val tree = new ComposeLogic("|", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte | yByte | xLong | yInt | xShort | xShort)
  }

}
