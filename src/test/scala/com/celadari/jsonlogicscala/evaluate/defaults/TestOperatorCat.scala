package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorCat extends TestString with TestNumeric {


  "Operator Cat xString + yString + zString" should "return value" in {
    val tree = new ComposeLogic("cat", Array(
      ValueLogic(Some(xString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(yString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(zString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xString + yString + zString
  }

  "Operator Cat xString + yDouble + zString" should "return value" in {
    val tree = new ComposeLogic("cat", Array(
      ValueLogic(Some(xString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(zString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xString + yDouble + zString
  }

  "Operator Cat Where is my mind" should "return value" in {
    val tree = new ComposeLogic("cat", Array(
      ValueLogic(Some("Where "), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some("is "), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some("my "), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some("mind"), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe "Where is my mind"
  }

}
