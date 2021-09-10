package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorNeg extends TestBoolean with TestNumeric with TestArray {


  "Operator Neg true" should "return false" in {
    val tree = new ComposeLogic("!", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !xBool
  }

  "Operator Neg false" should "return true" in {
    val tree = new ComposeLogic("!", Array(
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !yBool
  }

  "Operator Neg false java.lang.Boolean" should "return true" in {
    val tree = new ComposeLogic("!", Array(
      ValueLogic(Some(false: java.lang.Boolean), Some(SimpleTypeValue(BOOL_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !yBool
  }

  "Operator Neq non boolean input value" should "throw an exception" in {
    val tree = new ComposeLogic("!", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[IllegalInputException] should be thrownBy {throw thrown.origException}
    val expectedString = """ERROR AT: !
                           |└──\{ValueLogic Data '[a-zA-Z0-9]+-[a-zA-Z0-9]+-[a-zA-Z0-9]+-[a-zA-Z0-9]+-[a-zA-Z0-9]+': \[Ljava.lang.String;@[a-zA-Z0-9]+\}"""
      .stripMargin
    thrown.debugTreeString should fullyMatch regex expectedString
  }

  "Operator Neg And true false" should "return true" in {
    val tree = new ComposeLogic("!", Array(
      new ComposeLogic("and", Array(
        ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
        ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !(xBool && yBool)
  }

  "Operator Neg Or true false" should "return false" in {
    val tree = new ComposeLogic("!", Array(
      new ComposeLogic("or", Array(
        ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
        ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe !(xBool || yBool)
  }

}
