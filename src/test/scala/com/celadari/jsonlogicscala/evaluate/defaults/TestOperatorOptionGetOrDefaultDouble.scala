// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}


class TestOperatorOptionGetOrDefaultDouble extends TestNumeric with TestArray {

  "Operator OptionGetOrDefaultDouble Some(Double)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(Some(xDouble)), Some(OptionTypeValue(SimpleTypeValue(DOUBLE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator OptionGetOrDefaultDouble Some(java.lang.Double)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(Some(xDouble: java.lang.Double)), Some(OptionTypeValue(SimpleTypeValue(DOUBLE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator OptionGetOrDefaultDouble None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(DOUBLE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0d
  }

  "Operator OptionGetOrDefaultDouble Double" should "return value" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator OptionGetOrDefaultDouble java.lang.Double" should "return value" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(xDouble: java.lang.Double), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator OptionGetOrDefaultDouble option non double" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToDouble can only be applied to Option[Double] or Double. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultDouble non option non double" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_double", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToDouble can only be applied to Option[Double] or Double. Input condition: 5"
  }

}
