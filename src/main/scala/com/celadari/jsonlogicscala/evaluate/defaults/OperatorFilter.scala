// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}
import com.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorFilter extends CompositionOperator {

  /**
   * Checks number of conditions (equivalent to number of sub-nodes in syntax tree) is equal to 2.
   * Throws an [[com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException]] if number of conditions
   * different than 2.
   * @param conditions: array of conditions to check length's.
   */
  def checkInputs(conditions: Array[JsonLogicCore]): Unit = {
    if (conditions.length != 2) {
      val condString = conditions.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"Filter operator requires length of condition inputs array to equal 2.\nArray of conditions: $condString")
    }
  }

  /**
   * Returns array filtered by values that assert a given condition.
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

    values.filter(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue).asInstanceOf[java.lang.Boolean]
    })
  }
}
