// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}


object OperatorReduce extends CompositionOperator {

  /**
   * Checks number of conditions (equivalent to number of sub-nodes in syntax tree) is equal to 2.
   * Throws an [[com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException]] if number of conditions
   * different than 3.
   * @param conditions: array of conditions to check length's.
   */
  def checkInputs(conditions: Array[JsonLogicCore]): Unit = {
    if (conditions.length != 3) {
      val condString = conditions.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"Reduce operator requires length input conditions array to be exactly 3.\nArray of conditions: $condString")
    }
  }

  /**
   * Returns value by reducing array by a given expression.
   * @param values: values operator operates on.
   * @param logicArr: array of sub-nodes this composition operator operates one. Most of the time array is of length 1.
   * @param conditionCaller: node in syntax tree whose operator is this one.
   * @param reduceLogic: evaluator that called this method. Required to pass updated "logicOperatorToValue" recursively
   * to sub-tree and evaluate it.
   * @param logicOperatorToValue: map of (composition_operator -> map(variable_name -> variable_value)).
   * @return operated value by operator.
   */
  override def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    val jsonLogicComposition = logicArr(0)
    val initAccum = reduceLogic.evaluate(logicArr(1), logicOperatorToValue)

    values.foldLeft[Any](initAccum){case (value1, value2) => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("accumulator" -> value1, "current" -> value2))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue)
    }}
  }
}
