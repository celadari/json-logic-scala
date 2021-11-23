// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, OptionTypeValue, SimpleTypeValue}


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
