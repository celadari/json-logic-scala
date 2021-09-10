package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}


class TestOperatorOptionGetOrDefaultInt extends TestNumeric with TestArray {


  "Operator OptionGetOrDefaultInt Some(Int)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(Some(xInt)), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt Some(java.lang.Integer)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(Some(xInt: java.lang.Integer)), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0
  }

  "Operator OptionGetOrDefaultInt Int" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt java.lang.Integer" should "return value" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(xInt: java.lang.Integer), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator OptionGetOrDefaultInt option non integer" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToInt can only be applied to Option[Int] or Int. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultInt non option non integer" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_int", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToInt can only be applied to Option[Int] or Int. Input condition: 5"
  }

}
