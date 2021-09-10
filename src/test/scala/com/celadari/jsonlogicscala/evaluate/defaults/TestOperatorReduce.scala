package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.EvaluatorLogic
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, WrongNumberOfConditionsException}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}


class TestOperatorReduce extends TestNumeric with TestArray {


  "Operator Reduce (acc, el) => 5 * acc + el" should "return value" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("+", Array(
        new ComposeLogic("*", Array(
          ValueLogic(None, None, Some("accumulator"), None),
          ValueLogic(Some(5), Some(SimpleTypeValue(INT_CODENAME)))
        )),
        ValueLogic(None, None, Some("current"), None)
      )),
      ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.foldLeft[Int](0)(5 * _ + _)
  }

  "Operator Reduce (acc, el) => if (el <= 0) 2 * acc + el else 5 * acc + 3" should "return value" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrInt), Some(arrIntType)),
      new ComposeLogic("if", Array(
        new ComposeLogic("<=", Array(
          ValueLogic(None, None, Some("current"), None),
          ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)), None)
        )),
        new ComposeLogic("+", Array(
          new ComposeLogic("*", Array(
            ValueLogic(Some(2), Some(SimpleTypeValue(INT_CODENAME))),
            ValueLogic(None, None, Some("accumulator"), None)
          )),
          ValueLogic(None, None, Some("current"), None)
        )),
        new ComposeLogic("+", Array(
          new ComposeLogic("*", Array(
            ValueLogic(Some(5), Some(SimpleTypeValue(INT_CODENAME))),
            ValueLogic(None, None, Some("accumulator"), None)
          )),
          ValueLogic(Some(3), Some(SimpleTypeValue(INT_CODENAME)))
        ))
      )),
      ValueLogic(Some(0), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrInt.foldLeft[Int](0){case (acc, el) => if (el <= 0) 2 * acc + el else 5 * acc + 3}
  }

  "Operator Reduce more than 3 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("cat", Array(
        ValueLogic(None, None, Some("accumulator"), None),
        ValueLogic(Some(" "), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(None, None, Some("current"), None)
      )),
      ValueLogic(Some(""), Some(SimpleTypeValue(STRING_CODENAME))),
      ValueLogic(Some(""), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator Reduce less than 3 input conditions" should "throw an exception" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrString), Some(arrStringType))
    ))

    val evaluator = new EvaluatorLogic
    val thrown = the[EvaluateException] thrownBy {evaluator.eval(tree)}
    an[WrongNumberOfConditionsException] should be thrownBy {throw thrown.origException}
  }

  "Operator Reduce String (acc, el) => acc + el" should "return value" in {
    val tree = new ComposeLogic("reduce", Array(
      ValueLogic(Some(arrString), Some(arrStringType)),
      new ComposeLogic("cat", Array(
        ValueLogic(None, None, Some("accumulator"), None),
        ValueLogic(Some(" "), Some(SimpleTypeValue(STRING_CODENAME))),
        ValueLogic(None, None, Some("current"), None)
      )),
      ValueLogic(Some(""), Some(SimpleTypeValue(STRING_CODENAME)))
    ))

    val evaluator = new EvaluatorLogic
    evaluator.eval(tree) shouldBe arrString.foldLeft[String]("")(_ + " " + _)
  }

}
