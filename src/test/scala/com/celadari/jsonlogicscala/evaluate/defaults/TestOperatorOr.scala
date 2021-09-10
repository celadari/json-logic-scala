package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorOr extends TestBoolean with TestNumeric with TestArray {


  "Operator Or true true" should "return true" in {
    val tree = new ComposeLogic("or", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(zBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xBool || zBool)
  }

  "Operator Or (yLong >=0), (yLong==47)" should "return true" in {
    val tree = new ComposeLogic("or", Array(
      new ComposeLogic(">=", Array(
        ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME))),
        ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
      )),
      new ComposeLogic("==", Array(
        ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME))),
        ValueLogic(Some(47), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (yLong >= 0 || yLong == 47)
  }

  "Operator Or true false" should "return false" in {
    val tree = new ComposeLogic("or", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xBool || yBool)
  }

  "Operator Or (Some arrDouble >=0) (xBool)" should "return true" in {
    val tree = new ComposeLogic("or", Array(
      new ComposeLogic("some", Array(
        ValueLogic(Some(arrDouble), Some(arrDoubleType)),
        new ComposeLogic("==", Array(
          ValueLogic(None, None, Some(""), None),
          ValueLogic(Some(23), Some(SimpleTypeValue(INT_CODENAME)))
        ))
      )),
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (arrDouble.contains(23) || xBool)
  }

  "Operator Or (All arrDouble >=0) (xBool)" should "return false" in {
    val tree = new ComposeLogic("or", Array(
      new ComposeLogic("all", Array(
        ValueLogic(Some(arrDouble), Some(arrDoubleType)),
        new ComposeLogic("==", Array(
          ValueLogic(None, None, Some(""), None),
          ValueLogic(Some(23), Some(SimpleTypeValue(INT_CODENAME)))
        ))
      )),
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (arrDouble.forall(_ == 23) || xBool)
  }

  "Operator Or xBool yBool zBool" should "return false" in {
    val tree = new ComposeLogic("or", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(zBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xBool || yBool || zBool)
  }

}
