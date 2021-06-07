package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.github.celadari.jsonlogicscala.exceptions.{EvaluateException, IllegalInputException}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{OptionTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}

class TestOperatorOptionGetOrDefaultByte extends TestNumeric with TestArray {

  "Operator OptionGetOrDefaultByte Some(Byte)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(Some(xByte)), Some(OptionTypeValue(SimpleTypeValue(BYTE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xByte
  }

  "Operator OptionGetOrDefaultByte Some(java.lang.Byte)" should "return value" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(Some(xByte: java.lang.Byte)), Some(OptionTypeValue(SimpleTypeValue(BYTE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xByte
  }

  "Operator OptionGetOrDefaultByte None" should "return default value" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(None), Some(OptionTypeValue(SimpleTypeValue(BYTE_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe 0.toByte
  }

  "Operator OptionGetOrDefaultByte Byte" should "return value" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(xByte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xByte
  }

  "Operator OptionGetOrDefaultByte java.lang.Byte" should "return value" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(xByte: java.lang.Byte), Some(SimpleTypeValue(BYTE_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe xByte
  }

  "Operator OptionGetOrDefaultByte option non byte" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(Some(xLong)), Some(OptionTypeValue(SimpleTypeValue(LONG_CODENAME))))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToByte can only be applied to Option[Byte] or Byte. Input condition: Some(5)"
  }

  "Operator OptionGetOrDefaultByte non option non byte" should "thrown an exception" in {
    val tree = new ComposeLogic("get_or_default_byte", Array(
      ValueLogic(Some(xLong), Some(SimpleTypeValue(LONG_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrownEval = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    val thrown = the[IllegalInputException] thrownBy {throw thrownEval.origException}
    thrown.getMessage shouldBe "Operator OptionToByte can only be applied to Option[Byte] or Byte. Input condition: 5"
  }

}
