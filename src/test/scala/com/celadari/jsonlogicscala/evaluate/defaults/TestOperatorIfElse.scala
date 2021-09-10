package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, WrongNumberOfConditionsException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorIfElse extends TestNumeric with TestBoolean with TestArray {


  "Operator If xBool xByte else yByte" should "return value" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xByte
  }

  "Operator If yBool xByte else yByte" should "return value" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe yByte
  }

  "Operator If uBool xByte else if vBool yShort else xLong" should "return value" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(uBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(vBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator If xBool xFloat else if vBool yShort else xLong" should "return value" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(vBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator If uBool xFloat else if vBool yShort else if xBool xDouble else yInt" should "return value" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(uBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(vBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator IfElse less than 3 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator IfElse even number of input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(xBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(arrString), Some(arrStringType)),
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator If uBool xFloat else if vBool yShort else if yBool yDouble else yInt" should "return value" in {
    val tree = new ComposeLogic("if", Array(
      ValueLogic(Some(uBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(vBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yBool), Some(SimpleTypeValue(BOOL_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe yInt
  }

}
