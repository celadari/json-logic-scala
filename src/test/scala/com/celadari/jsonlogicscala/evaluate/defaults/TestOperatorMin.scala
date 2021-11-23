// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorMin extends TestNumeric {


  "Operator Min Byte Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xByte
    result.isInstanceOf[Byte] shouldBe true
  }

  "Operator Min Byte Short" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xShort
    result.isInstanceOf[Short] shouldBe true
  }

  "Operator Min Byte Int" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xInt
    result.isInstanceOf[Int] shouldBe true
  }

  "Operator Min Byte Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xLong
    result.isInstanceOf[Long] shouldBe true
  }

  "Operator Min Byte Float" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xFloat
    result.isInstanceOf[Float] shouldBe true
  }

  "Operator Min Double Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xDouble
    result.isInstanceOf[Double] shouldBe true
  }

  "Operator Min Short Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xShort
  }

  "Operator Min Short Short" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xShort
  }

  "Operator Min Short Integer" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator Min Short Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator Min Short Float" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator Min Short Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Int Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator Min Int Short" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator Min Long Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator Min Long Short" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator Min Long Int" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator Min Long Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator Min Long Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Float Byte" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator Min Float Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator Min Float Float" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator Min Float Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Double Short" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Double Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Double Float" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Byte Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Int Int" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xInt
  }

  "Operator Min Int Long" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xLong
  }

  "Operator Min Int Float" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xFloat
  }

  "Operator Min Int Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Double Int" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min Double Double" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xDouble
  }

  "Operator Min different types" should "return value" in {
    val tree = new ComposeLogic("min", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val result = evaluator.eval(tree)
    result shouldBe xFloat
    result.isInstanceOf[Float] shouldBe true
  }

}
