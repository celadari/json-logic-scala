package com.celadari.jsonlogicscala.evaluate

object EvaluatorValueLogic {
  val IDENTITY_REDUCER_VALUELOGIC: EvaluatorValueLogic = new EvaluatorValueLogic {
    override def evaluateValueLogic(value: Any): Any = value
  }
}

trait EvaluatorValueLogic {

  def evaluateValueLogic(value: Any): Any
}
