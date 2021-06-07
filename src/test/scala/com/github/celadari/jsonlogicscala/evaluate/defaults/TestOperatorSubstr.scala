package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluateException, WrongNumberOfConditionsException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorSubstr extends TestMap with TestNumeric with TestArray with TestString {


  "Operator Substr uString 4" should "return value" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.slice(4, uString.length)
  }

  "Operator Substr uString -4" should "return value" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(-4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.slice(uString.length - 4, uString.length)
  }

  "Operator Substr uString 4 2" should "return value" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.slice(4, 6)
  }

  "Operator Substr less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator Substr more than 3 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator Substr uString 4 -2" should "return value" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(-2), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.slice(4, uString.length - 2)
  }

  "Operator Substr uString -4 2" should "return value" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(-4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.slice(uString.length - 4, uString.length - 2)
  }

  "Operator Substr uString -4 -1" should "return value" in {
    val tree = new ComposeLogic("substr", Array(
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(-4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(-1), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.slice(uString.length - 4, uString.length - 1)
  }

}
