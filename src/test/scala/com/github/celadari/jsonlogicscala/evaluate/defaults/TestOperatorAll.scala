package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluateException, WrongNumberOfConditionsException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorAll extends TestNumeric with TestArray {

  "Operator All f(el: Boolean => el)" should "return value" in {
    val tree = new ComposeLogic("all", Array(
      ValueLogic(Some(arrBool), Some(arrBoolType)),
      ValueLogic(None, None, Some(""), None)
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrBool.forall(_.booleanValue())
  }

  "Operator All f(el: Float => el + 1 <= 0)" should "return value" in {
    val tree = new ComposeLogic("all", Array(
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
    evaluator.eval(tree) shouldBe arrInt.forall(_ + 1 <= 0)
  }


  "Operator All f(el: String => el.substring(string0))" should "return value" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("all", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrString.forall(string0.contains)
  }

  "Operator All more than 2 input conditions" should "throw an exception" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("all", Array(
      ValueLogic(Some(arrString), Some(arrStringType), None, Some("data1")),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME)), None, Some("data2"))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType), None, Some("data3"))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator All less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("all", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator All f(el: Float => el**2 + el + 3 == 4)" should "return value" in {
    val tree = new ComposeLogic("all", Array(
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
    evaluator.eval(tree) shouldBe arrDouble.forall(el => el*el + el + 3 == 4)
  }

}
