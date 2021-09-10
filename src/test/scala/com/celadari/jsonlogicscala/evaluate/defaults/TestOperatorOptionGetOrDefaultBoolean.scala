package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}


class TestOperatorOptionGetOrDefaultBoolean extends TestNumeric with TestBoolean {

  "Operator OptionGetOrDefaultBoolean Some(Boolean)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(Some(xBool)), Some(OptionTypeValue(SimpleTypeValue(BOOL_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xBool
  }

  "Operator OptionGetOrDefaultBoolean Some(java.lang.Boolean)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(Some(xBool: java.lang.Boolean)), Some(OptionTypeValue(SimpleTypeValue(BOOL_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xBool
  }

  "Operator OptionGetOrDefaultBoolean None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(BOOL_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe false
  }

  "Operator OptionGetOrDefaultBoolean Boolean" should "return value" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xBool
  }

  "Operator OptionGetOrDefaultBoolean java.lang.Boolean" should "return value" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(xBool: java.lang.Boolean), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xBool
  }

  "Operator OptionGetOrDefaultBoolean option non boolean" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToBoolean can only be applied to Option[Boolean] or Boolean. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultBoolean non option non boolean" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_boolean", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToBoolean can only be applied to Option[Boolean] or Boolean. Input condition: 5"
  }

}
