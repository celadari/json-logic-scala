package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore}
import com.celadari.jsonlogicscala.evaluate.{CompositionOperator, EvaluatorLogic}
import com.celadari.jsonlogicscala.exceptions.WrongNumberOfConditionsException


object OperatorFilter extends CompositionOperator {

  def checkInputs(conditions: Array[JsonLogicCore]): Unit = {
    if (conditions.length != 2) {
      val condString = conditions.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"Filter operator requires length of condition inputs array to equal 2.\nArray of conditions: $condString")
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

    values.filter(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue).asInstanceOf[java.lang.Boolean]
    })
  }
}
