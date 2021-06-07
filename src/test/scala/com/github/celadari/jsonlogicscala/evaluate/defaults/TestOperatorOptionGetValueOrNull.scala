package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic

class TestOperatorOptionGetValueOrNull extends TestNumeric with TestArray {

  "Operator OptionGetValueOrNull Some(Int)" should "return value" in {
    val tree = new ComposeLogic("get_value_or_null", Array(
      ValueLogic(Some(Some(arrInt)), Some(OptionTypeValue(ArrayTypeValue(SimpleTypeValue(INT_CODENAME)))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt
  }

  "Operator OptionGetValueOrNull Array" should "return value" in {
    val tree = new ComposeLogic("get_value_or_null", Array(
      ValueLogic(Some(arrInt), Some(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt
  }

  "Operator OptionGetValueOrNull None" should "return default value" in {
    val tree = new ComposeLogic("get_value_or_null", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(INT_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe (null: Any)
  }

}
