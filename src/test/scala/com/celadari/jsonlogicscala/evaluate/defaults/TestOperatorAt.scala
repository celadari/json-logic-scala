package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException, WrongNumberOfConditionsException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorAt extends TestMap with TestNumeric with TestArray {


  "Operator At arrInt" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt(2)
  }

  "Operator At arrInt with Double Index" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      new ComposeLogic("+", Array(
        ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME))),
        ValueLogic(Some(2.0d), Some(SimpleTypeValue(DOUBLE_CODENAME)))
      )),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt((1 + 2.0d).toInt)
  }

  "Operator At mapInt Index" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some("plane"), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(mapInt), Some(mapIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapInt("plane")
  }

  "Operator At Iterable[Int] Index" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(Seq(4, 5)), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 4
  }

  "Operator At At mapMapLong" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some("boat"), Some(SimpleTypeValue(STRING_CODENAME))),
      new ComposeLogic("at", Array(
        ValueLogic(Some("navy"), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(Some(mapMapLong), Some(mapMapLongType))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapMapLong("navy")("boat")
  }

  "Operator At At mapArrDouble" should "return value" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME))),
      new ComposeLogic("at", Array(
        ValueLogic(Some("plane"), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(Some(mapArrDouble), Some(mapMapLongType))
      ))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe mapArrDouble("plane")(1)
  }

  "Operator At more than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME))),
      new ComposeLogic("at", Array(
        ValueLogic(Some("plane"), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(Some(mapArrDouble), Some(mapMapLongType)),
        ValueLogic(Some("plane"), Some(SimpleTypeValue(STRING_CODENAME)))
      ))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator At less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator At Index Index" should "throw an exception" in {
    val tree = new ComposeLogic("at", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[IllegalInputException] should be thrownBy {throw thrown.origException}
  }

}
