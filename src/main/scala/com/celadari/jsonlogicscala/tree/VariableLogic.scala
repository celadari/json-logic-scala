package com.celadari.jsonlogicscala.tree

case class VariableLogic(
                          variableName: String,
                          composeOperator: ComposeLogic
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME)
