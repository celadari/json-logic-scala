// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.impl

import com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic


class EvaluatorValueLogicImplInt(val num: Int) extends EvaluatorValueLogic {

  override def equals(that: Any): Boolean = {
    that match {
      case evaluator: EvaluatorValueLogicImplInt => num == evaluator.num
      case _ => false
    }
  }

  override def hashCode(): Int = num.hashCode()

  override def evaluateValueLogic(value: Any): Any = {
    num + value.asInstanceOf[Int]
  }
}
