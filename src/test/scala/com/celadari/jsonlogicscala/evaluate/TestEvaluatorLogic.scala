// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate

import com.celadari.jsonlogicscala.exceptions.{IllegalInputException, IncompatibleMethodsException}
import com.celadari.jsonlogicscala.tree.{ComposeLogic, ValueLogic}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue
import com.celadari.jsonlogicscala.TestPrivateMethods


class TestEvaluatorLogic extends TestPrivateMethods {

  private[this] val evaluateValueLogic = PrivateMethod[Any](toSymbol("evaluateValueLogic"))
  private[this] val evaluateComposeLogic = PrivateMethod[Any](toSymbol("evaluateComposeLogic"))

  "Evaluate Value Logic with default ValueLogicReducer" should "return value" in {
    val evaluator = new EvaluatorLogic
    val result1 = evaluator invokePrivate evaluateValueLogic(ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))))
    val result2 = evaluator invokePrivate evaluateValueLogic(ValueLogic(Some(2), None))
    result1 shouldBe 4
    result2 shouldBe (null: Any)
  }

  "Evaluate Value Logic with custom ValueLogicReducer" should "return value" in {
    val customIntValueLogicReducer = new EvaluatorValueLogic {
      override def evaluateValueLogic(value: Any): Any = "Custom EvaluatorValueLogic"
    }

    implicit val conf: EvaluatorLogicConf = EvaluatorLogicConf.createConf(
      evaluatorValueLogicManualAdd = Map(SimpleTypeValue("customInt") -> customIntValueLogicReducer)
    )
    val evaluator = new EvaluatorLogic
    val result1 = evaluator invokePrivate evaluateValueLogic(ValueLogic(Some(4), Some(SimpleTypeValue("customInt"))))
    val result2 = evaluator invokePrivate evaluateValueLogic(ValueLogic(Some(2), None))
    result1 shouldBe "Custom EvaluatorValueLogic"
    result2 shouldBe (null: Any)
  }

  "Evaluate Compose Logic reduceType default operators" should "return value" in {
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("+", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    val result = evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())
    result shouldBe 8
  }

  "Evaluate Compose Logic non-defined operator" should "throw exception" in {
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("non-defined", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    an[IllegalInputException] shouldBe thrownBy {evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())}
  }

  "Evaluate Compose Logic non-existing method of nonReduce type operator" should "throw exception" in {
    object FooOperator extends Operator
    implicit val conf: EvaluatorLogicConf = EvaluatorLogicConf.createConf(
      methodConfsManualAdd = Map("foo" -> MethodConf("foo", "nonexisting", Some(FooOperator), isReduceTypeOperator = false)) ++
        EvaluatorLogicConf.DEFAULT_METHOD_CONFS
    )
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("foo", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    an[IncompatibleMethodsException] shouldBe thrownBy {evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())}
  }

  "Evaluate Compose Logic non-existing method of reduce type operator" should "throw exception" in {
    object FooOperator extends Operator
    implicit val conf: EvaluatorLogicConf = EvaluatorLogicConf.createConf(
      methodConfsManualAdd = Map("foo" -> MethodConf("foo", "nonexisting", Some(FooOperator))) ++ EvaluatorLogicConf.DEFAULT_METHOD_CONFS
    )
    val evaluator = new EvaluatorLogic

    val composeLogic = new ComposeLogic("foo", Array(
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME))),
      ValueLogic(Some(4), Some(SimpleTypeValue(INT_CODENAME)))
    ))

    an[IncompatibleMethodsException] shouldBe thrownBy {evaluator invokePrivate evaluateComposeLogic(composeLogic, Map[ComposeLogic, Map[String, Any]]())}
  }

}
