package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}


class TestOperatorOptionGetOrDefaultLong extends TestNumeric with TestArray {

  "Operator OptionGetOrDefaultLong Some(Long)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator OptionGetOrDefaultLong Some(java.lang.Long)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(Some(xLong: java.lang.Long)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator OptionGetOrDefaultLong None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0L
  }

  "Operator OptionGetOrDefaultLong Long" should "return value" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator OptionGetOrDefaultLong java.lang.Long" should "return value" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(xLong: java.lang.Long), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator OptionGetOrDefaultLong option non long" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(Some(xDouble)), Some(OptionTypeValue(SimpleTypeValue(DOUBLE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToLong can only be applied to Option[Long] or Long. Input condition: Some(5.0)"
  }

  "Operator OptionGetOrDefaultLong non option non long" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_long", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToLong can only be applied to Option[Long] or Long. Input condition: 5.0"
  }

}
