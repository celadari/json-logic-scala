// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}


class TestOperatorOptionGetOrDefaultShort extends TestNumeric with TestArray {

  "Operator OptionGetOrDefaultShort Some(Short)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(Some(xShort)), Some(OptionTypeValue(SimpleTypeValue(SHORT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xShort
  }

  "Operator OptionGetOrDefaultShort Some(java.lang.Short)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(Some(xShort: java.lang.Short)), Some(OptionTypeValue(SimpleTypeValue(SHORT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xShort
  }

  "Operator OptionGetOrDefaultShort None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(SHORT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0.toShort
  }

  "Operator OptionGetOrDefaultShort Short" should "return value" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xShort
  }

  "Operator OptionGetOrDefaultShort java.lang.Short" should "return value" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(xShort: java.lang.Short), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xShort
  }

  "Operator OptionGetOrDefaultShort option non short" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToShort can only be applied to Option[Short] or Short. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultShort non option non short" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_short", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToShort can only be applied to Option[Short] or Short. Input condition: 5"
  }

}
