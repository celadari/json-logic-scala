// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorPow extends TestNumeric {

  "Operator Pow Byte ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xByte.toInt: java.lang.Integer, yByte.toInt: java.lang.Integer)
  }

  "Operator Pow Byte ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xByte.toInt: java.lang.Integer, yShort.toInt: java.lang.Integer)
  }

  "Operator Pow Byte ** Int" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xInt, yInt)
  }

  "Operator Pow Byte ** Long" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xByte.toLong, yLong)
  }

  "Operator Pow Byte ** Float" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xByte.toDouble, yFloat.toDouble)
  }

  "Operator Pow Byte ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xByte.toDouble, yDouble)
  }

  "Operator Pow Short ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xShort.toInt: java.lang.Integer, yByte.toInt: java.lang.Integer)
  }

  "Operator Pow Short ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xShort.toInt: java.lang.Integer, yShort.toInt: java.lang.Integer)
  }

  "Operator Pow Short ** Integer" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xShort.toInt, yInt)
  }

  "Operator Pow Short ** Long" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xShort.toLong, yLong)
  }

  "Operator Pow Short ** Float" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xShort.toDouble, yFloat.toDouble)
  }

  "Operator Pow Short ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xShort.toDouble, yDouble)
  }

  "Operator Pow Int ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xInt, yByte.toInt)
  }

  "Operator Pow Int ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xInt, yShort.toInt)
  }

  "Operator Pow Int ** Int" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xInt, yInt)
  }

  "Operator Pow Int ** Float" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xInt.toDouble, yFloat.toDouble)
  }

  "Operator Pow Int ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xInt.toDouble, yDouble)
  }

  "Operator Pow Long ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xLong, yByte.toLong)
  }

  "Operator Pow Long ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xLong, yShort.toLong)
  }

  "Operator Pow Long ** Int" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xLong, yInt.toLong)
  }

  "Operator Pow Long ** Long" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe OperatorPow.pow(xLong, yLong)
  }

  "Operator Pow Long ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xLong.toDouble, yDouble)
  }

  "Operator Pow Float ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xFloat.toDouble, yByte.toDouble)
  }

  "Operator Pow Float ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xFloat.toDouble, yShort.toDouble)
  }

  "Operator Pow Float ** Int" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xFloat.toDouble, yInt.toDouble)
  }

  "Operator Pow Float ** Long" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xFloat.toDouble, yLong.toDouble)
  }

  "Operator Pow Float ** Float" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xFloat.toDouble, yFloat.toDouble)
  }

  "Operator Pow Float ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xFloat.toDouble, yDouble)
  }

  "Operator Pow Double ** Byte" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xDouble, yByte.toDouble)
  }

  "Operator Pow Double ** Short" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xDouble, yShort.toDouble)
  }

  "Operator Pow Double ** Long" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xDouble, yLong.toDouble)
  }

  "Operator Pow Double ** Float" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yFloat), Some(SimpleTypeValue(FLOAT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xDouble, yFloat.toDouble)
  }

  "Operator Pow Double ** Double" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xDouble), Some(SimpleTypeValue(DOUBLE_CODENAME))),
      ValueLogic(Some(yDouble), Some(SimpleTypeValue(DOUBLE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe math.pow(xDouble, yDouble)
  }

  "Operator Pow different types" should "return value" in {
    val tree = new ComposeLogic("**", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(yByte), Some(SimpleTypeValue(BYTE_CODENAME))),
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME))),
      ValueLogic(Some(xFloat), Some(SimpleTypeValue(FLOAT_CODENAME))),
      ValueLogic(Some(yInt), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME))),
      ValueLogic(Some(xShort), Some(SimpleTypeValue(SHORT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe
      math.pow(
        math.pow(
          math.pow(
            math.pow(
              OperatorPow.pow(OperatorPow.pow(xByte.toInt: java.lang.Integer, yByte.toInt: java.lang.Integer).toLong, xLong).toDouble,
              xFloat.toDouble
            ),
            yInt.toDouble
          ),
          xShort.toDouble
        ),
        xShort.toDouble
      )
  }
}
