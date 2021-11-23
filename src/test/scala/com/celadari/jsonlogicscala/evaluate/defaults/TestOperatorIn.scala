// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, WrongNumberOfConditionsException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorIn extends TestMap with TestNumeric with TestArray with TestString {


  "Operator In 2 arrInt" should "return false" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.contains(2)
  }

  "Operator In 56 arrInt" should "return true" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(56), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.contains(56)
  }

  "Operator In arr arrArrDouble" should "return true" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(Array(67d, 49d, 9d)), Some(arrDoubleType)),
      ValueLogic(Some(arrArrDouble), Some(arrArrDoubleType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrListDouble.contains(List(67d, 49d, 9d))
  }

  "Operator In arr arrArrInt" should "return true" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(Array(34, 56, 89)), Some(arrIntType)),
      ValueLogic(Some(arrArrInt), Some(arrArrIntType))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrListInt.contains(List(34, 56, 89))
  }

  "Operator In tString uString" should "return true" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(tString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(uString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe uString.contains(tString)
  }

  "Operator In yString zString" should "return false" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(yString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(zString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe zString.contains(yString)
  }

  "Operator In more than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(1), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(arrInt), Some(arrIntType)),
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator In less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(arrInt), Some(arrIntType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator String In more than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(xString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(yString), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(zString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator String In less than 2 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("in", Array(
      ValueLogic(Some(xString), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

}
