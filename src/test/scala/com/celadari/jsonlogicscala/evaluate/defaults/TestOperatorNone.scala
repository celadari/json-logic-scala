// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, WrongNumberOfConditionsException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorNone extends TestNumeric with TestArray {

  "Operator None f(el: Boolean => el)" should "return value" in {
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrBool), Some(arrBoolType)),
      ValueLogic(None, None, Some(""), None)
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (!arrBool.exists(_.booleanValue()))
  }

  "Operator None f(el: Float => el + 1 <= 0)" should "return value" in {
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("<=", Array(
        new ComposeLogic("+", Array(
          ValueLogic(None, None, Some(""), None),
          ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !arrInt.exists(_ + 1 <= 0)
  }


  "Operator None f(el: String => el.substring(string0))" should "return value" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !arrString.exists(string0.contains)
  }

  "Operator None more than 2 input conditions" should "throw an exception" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME)))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator None less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator None f(el: Float => el**2 + el + 3 == 4)" should "return value" in {
    val tree = new ComposeLogic("none", Array(
      ValueLogic(Some(arrDouble), Some(arrDoubleType)),
      new ComposeLogic("==", Array(
        new ComposeLogic("+", Array(
          new ComposeLogic("**", Array(
            ValueLogic(None, None, Some(""), None),
            ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
          )),
          ValueLogic(None, None, Some(""), None),
          ValueLogic(Some(3), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !arrDouble.exists(el => el*el + el + 3 == 4)
  }

}
