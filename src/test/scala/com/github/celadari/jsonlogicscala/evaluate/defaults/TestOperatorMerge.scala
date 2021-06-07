package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorMerge extends TestMap with TestNumeric with TestArray with TestString {


  "Operator Merge arrInt arrInt" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt ++ arrInt
  }

  "Operator Merge arrInt arrDouble" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      ValueLogic(Some(arrDouble), Some(arrDoubleType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt ++ arrDouble
  }

  "Operator Merge arrArrInt arrArrInt" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(arrArrInt), Some(arrArrIntType)),
      ValueLogic(Some(arrArrInt), Some(arrArrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrArrInt ++ arrArrInt
  }

  "Operator Merge arrArrInt arrArrDouble" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(arrArrInt), Some(arrArrIntType)),
      ValueLogic(Some(arrArrDouble), Some(arrArrDoubleType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrArrInt ++ arrArrDouble
  }

  "Operator Merge arrInt xInt" should "return value" in {
    val tree = new ComposeLogic("merge", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt ++ Array(xInt)
  }

}
