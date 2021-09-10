package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}


object OperatorReduce extends CompositionOperator {

  def checkInputs(conditions: Array[JsonLogicCore]): Unit = {
    if (conditions.length != 3) {
      val condString = conditions.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"Reduce operator requires length input conditions array to be exactly 3.\nArray of conditions: $condString")
    }
  }

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
