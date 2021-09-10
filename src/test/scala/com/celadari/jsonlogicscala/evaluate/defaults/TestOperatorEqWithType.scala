package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorEqWithType extends TestNumeric {


  "Operator EqStrict Byte == Byte" should "return false" in {
    val tree = new ComposeLogic("==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte == yByte)
  }

  "Operator EqStrict Byte == Short" should "return true" in {
    val tree = new ComposeLogic("==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte == xShort)
  }

  "Operator EqStrict Int == Int" should "return true" in {
    val tree = new ComposeLogic("==", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xInt == xInt)
  }

  "Operator EqStrict Byte == Long" should "return false" in {
    val tree = new ComposeLogic("==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte == yLong)
  }

  "Operator EqStrict Byte == Float" should "return true" in {
    val tree = new ComposeLogic("==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte == xFloat)
  }

  "Operator EqStrict Byte == Byte" should "return true" in {
    val tree = new ComposeLogic("==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xByte == xByte)
  }

}
