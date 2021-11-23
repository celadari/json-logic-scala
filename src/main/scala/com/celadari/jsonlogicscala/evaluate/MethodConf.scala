// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate


/**
 * Represents an operator's information for evaluation.
 * @param operator: codename.
 * @param methodName: name of method to invoke at evaluation.
 * @param ownerMethodOpt: object the method to be invoked belongs to if defined, else means the method belongs to accumulator at evaluation.
 * @param isReduceTypeOperator: true if operator is a reduce type (max, min, +, -, *, /, And, Or, ...), false otherwise.
 * @param isCompositionOperator: true if operator is a composition type (reduce, map, filter, all, none, some), false otherwise.
 * @param isUnaryOperator: true if unary operator, false otherwise.
 */
case class MethodConf(
                       operator: String,
                       methodName: String,
                       ownerMethodOpt: Option[Operator],
                       isReduceTypeOperator: Boolean = true,
                       isCompositionOperator: Boolean = false,
                       isUnaryOperator: Boolean = false
                     )
