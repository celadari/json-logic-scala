// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.impl

import com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic


object EvaluatorValueLogicImplString extends EvaluatorValueLogic {

  override def toString: String = this.getClass.getName
  override def evaluateValueLogic(value: Any): Any = {
    value.asInstanceOf[String]
  }
}
