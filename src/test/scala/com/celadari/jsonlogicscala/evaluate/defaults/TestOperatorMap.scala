package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException, WrongNumberOfConditionsException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.{MapTypeValue, SimpleTypeValue}


class TestOperatorMap extends TestNumeric with TestArray {


  "Operator Map f(el: Float => el + 1)" should "return value" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("+", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.map(_ + xFloat)
  }

  "Operator Map f(el: Boolean => if (el) xFloat else xDouble)" should "return value" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrBool), Some(arrBoolType)),
      new ComposeLogic("if", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
        ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrBool.map(bool => if (bool) xFloat else yDouble)
  }


  "Operator Map f(el: String => map0(el))" should "return value" in {
    val map0 = Map("I" -> "You", "love" -> "prefer", "walking" -> "wandering", "in" -> "around", "New York" -> "Paris")
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("at", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(map0), Some(MapTypeValue(SimpleTypeValue(STRING_CODENAME))))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrString.map(map0.apply)
  }

  "Operator Map composed with unknown operator" should "throw an exception" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrString), Some(arrStringType), None, Some("data1")),
      new ComposeLogic("unknown_operator", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data2"))
      ))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEvaluate = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEvaluate.origException}
    thrown.getMessage shouldBe "No configuration for operator: unknown_operator"
  }

  "Operator Map more than 2 input conditions" should "throw an exception" in {
    val string0 = "You love New York"
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("in", Array(
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(string0), Some(SimpleTypeValue(STRING_CODENAME)))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator Map less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator Map f(el: Float => el**2 + el + 3)" should "return value" in {
    val tree = new ComposeLogic("map", Array(
      ValueLogic(Some(arrDouble), Some(arrDoubleType)),
      new ComposeLogic("+", Array(
        new ComposeLogic("**", Array(
          ValueLogic(None, None, Some(""), None),
          ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(None, None, Some(""), None),
        ValueLogic(Some(3), Some(SimpleTypeValue(INT_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrDouble.map(el => el*el + el + 3)
  }

}
