package com.github.celadari.jsonlogicscala.evaluate.impl

import com.github.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic

class EvaluatorValueLogicImplFloat(val num1: Float, val num2: Float) extends EvaluatorValueLogic {

  override def equals(that: Any): Boolean = {
    that match {
      case evaluator: EvaluatorValueLogicImplFloat => (num1 == evaluator.num1) && (num2 == evaluator.num2)
      case _ => false
    }
  }

  override def hashCode(): Int = {
    num1.hashCode() + num2.hashCode()
  }

  override def evaluateValueLogic(value: Any): Any = {
    0f + value.asInstanceOf[Int]
  }
}
