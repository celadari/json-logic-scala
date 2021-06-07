package com.github.celadari.jsonlogicscala.evaluate

case class MethodConf(
                       operator: String,
                       methodName: String,
                       ownerMethodOpt: Option[Operator],
                       isReduceTypeOperator: Boolean = true,
                       isCompositionOperator: Boolean = false,
                       isUnaryOperator: Boolean = false
                     )
