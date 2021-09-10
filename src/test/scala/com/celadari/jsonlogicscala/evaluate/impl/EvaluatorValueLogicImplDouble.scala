package com.celadari.jsonlogicscala.evaluate.impl

import com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic


object EvaluatorValueLogicImplDouble extends EvaluatorValueLogic {

  override def toString: String = this.getClass.getName
  override def evaluateValueLogic(value: Any): Any = {
    value.asInstanceOf[Double]
  }
}
