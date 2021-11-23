// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree


/**
 * Represents an internal node in syntax tree. It is an operator node that represents a variable node in a composed operator.
 * Scala data structures representing expressions from json-logic-typed format are based on abstract syntax tree.
 * @param variableName: string codename.
 * @param composeOperator: sub-parent node this node is variable of.
 */
case class VariableLogic(
                          variableName: String,
                          composeOperator: ComposeLogic
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME)
