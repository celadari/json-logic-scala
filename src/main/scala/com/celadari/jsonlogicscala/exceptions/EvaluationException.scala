// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.exceptions

import com.celadari.jsonlogicscala.tree.JsonLogicCore


object EvaluationException {
  def unapply(evaluationException: EvaluationException): Option[(String, JsonLogicCore, Throwable)] = {
    Some((evaluationException.getMessage, evaluationException.condition, evaluationException.origException))
  }
}

final class EvaluationException(
                                 msg: String,
                                 val condition: JsonLogicCore,
                                 val origException: Throwable
                               )
  extends JsonLogicScalaException(msg)
