package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorNEqWithTypeCoercion extends TestNumeric {


  "Operator NEqWithTypeCoercion Byte !== Byte" should "return true" in {
    val tree = new ComposeLogic("!==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xByte.equals(yByte)
  }

  "Operator NEqWithTypeCoercion Byte !== Short" should "return true" in {
    val tree = new ComposeLogic("!==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xByte.equals(xShort)
  }

  "Operator NEqWithTypeCoercion Int !== Int" should "return false" in {
    val tree = new ComposeLogic("!==", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xInt.equals(xInt)
  }

  "Operator NEqWithTypeCoercion Byte !== Long" should "return true" in {
    val tree = new ComposeLogic("!==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xByte.equals(yLong)
  }

  "Operator NEqWithTypeCoercion Byte !== Float" should "return true" in {
    val tree = new ComposeLogic("!==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xByte.equals(xFloat)
  }

  "Operator NEqWithTypeCoercion Byte !== Byte" should "return false" in {
    val tree = new ComposeLogic("!==", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xByte.equals(xByte)
  }

}
