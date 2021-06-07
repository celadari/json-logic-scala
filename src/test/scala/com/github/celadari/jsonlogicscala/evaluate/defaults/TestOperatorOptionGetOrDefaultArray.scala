package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOptionGetOrDefaultArray extends TestNumeric with TestArray {

  "Operator OptionGetOrDefaultArray Some(Array)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_array", Array(
      ValueLogic(Some(Some(arrInt)), Some(OptionTypeValue(ArrayTypeValue(SimpleTypeValue(INT_CODENAME)))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt
  }

  "Operator OptionGetOrDefaultArray Array" should "return value" in {
    val tree = new ComposeLogic("get_or_default_array", Array(
      ValueLogic(Some(arrInt), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt
  }

  "Operator OptionGetOrDefaultArray None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_array", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe Array[Any]()
  }

  "Operator OptionGetOrDefaultArray option non array" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_array", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToArray can only be applied to Option[Array[_]] or Array[_]. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultArray non option non array" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_array", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToArray can only be applied to Option[Array[_]] or Array[_]. Input condition: 5"
  }

}
