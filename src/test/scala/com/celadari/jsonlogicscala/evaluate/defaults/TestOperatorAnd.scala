// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorAnd extends TestBoolean with TestNumeric with TestArray {


  "Operator And true true" should "return true" in {
    val tree = new ComposeLogic("and", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(zBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xBool && zBool)
  }

  "Operator And (yLong >=0), (yLong==47)" should "return true" in {
    val tree = new ComposeLogic("and", Array(
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
    evaluator.eval(tree) shouldBe (yLong >= 0 && yLong == 47)
  }

  "Operator And true false" should "return false" in {
    val tree = new ComposeLogic("and", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xBool && yBool)
  }

  "Operator And (Some arrDouble >=0) (xBool)" should "return true" in {
    val tree = new ComposeLogic("and", Array(
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
    evaluator.eval(tree) shouldBe (arrDouble.contains(23) && xBool)
  }

  "Operator And (All arrDouble >=0) (xBool)" should "return false" in {
    val tree = new ComposeLogic("and", Array(
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
    evaluator.eval(tree) shouldBe (arrDouble.forall(_ == 23) && xBool)
  }

  "Operator And xBool yBool zBool" should "return false" in {
    val tree = new ComposeLogic("and", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(zBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (xBool && yBool && zBool)
  }

}
