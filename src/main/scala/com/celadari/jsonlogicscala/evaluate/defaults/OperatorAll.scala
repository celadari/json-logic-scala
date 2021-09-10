package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}
import com.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorAll extends CompositionOperator {

  def checkInputs(conditions: Array[JsonLogicCore]): Unit = {
    if (conditions.length != 2) {
      val condString = conditions.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"All operator requires length of condition inputs array to be exactly 2.\nArray of conditions: $condString")
    }
  }

  def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    val jsonLogicComposition = logicArr(0)

    values.forall(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue).asInstanceOf[java.lang.Boolean]
    })
  }
}
