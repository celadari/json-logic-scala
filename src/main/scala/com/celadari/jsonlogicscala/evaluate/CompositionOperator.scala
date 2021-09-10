package com.celadari.jsonlogicscala.evaluate

import com.celadari.jsonlogicscala.exceptions.{EvaluateException, EvaluationException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}


object CompositionOperator {

  class ComposeJsonLogicCore(protected[this] val jsonLogicVariable: JsonLogicCore) (implicit val conf: EvaluatorLogicConf) {
    private[this] var jsonLogicTraversed: JsonLogicCore = _
    private[this] var isEvaluated: Boolean = false

    protected[this] def traverse(jsonLogicCore: JsonLogicCore, parentComposeLogicOperatorOpt: Option[ComposeLogic]): JsonLogicCore = {
      try {
        jsonLogicCore match {
          case ComposeLogic(operator, conditions) => {
            val composeLogic = new ComposeLogic(operator, Array())
            val confMethod = conf.operatorToMethodConf.getOrElse(operator, {
              throw new IllegalInputException(s"No configuration for operator: $operator")
            })

            val conditionsTraversed = if (confMethod.isCompositionOperator) {
              conditions.headOption.toArray.map(cond => traverse(cond, parentComposeLogicOperatorOpt)) ++
                conditions.tail.map(cond => traverse(cond, Some(composeLogic)))
            } else conditions.map(cond => traverse(cond, parentComposeLogicOperatorOpt))
            composeLogic.conditions = conditionsTraversed
            composeLogic
          }
          case ValueLogic(_, _, variableNameOpt, _) if variableNameOpt.isDefined => VariableLogic(variableNameOpt.get, parentComposeLogicOperatorOpt.get)
          case ValueLogic(_, _, variableNameOpt, _) if variableNameOpt.isEmpty => jsonLogicCore
        }
      }
      catch {
        case evaluationException: EvaluationException => throw evaluationException
        case throwable: Throwable => throw new EvaluationException(throwable.getMessage, jsonLogicCore, throwable)
      }
    }

    def evaluate(): JsonLogicCore = {
      try {
        if (!isEvaluated) {
          jsonLogicTraversed = traverse(jsonLogicVariable, None)
          isEvaluated = true
        }
        jsonLogicTraversed
      }
      catch {
        case EvaluationException(msg, condition, origExcept) => throw new EvaluateException(msg, condition, jsonLogicVariable, origExcept)
      }
    }
  }

}

trait CompositionOperator extends Operator {

  def composeOperator(
                       values: Array[Any],
                       logicArr: Array[JsonLogicCore],
                       conditionCaller: ComposeLogic,
                       reduceLogic: EvaluatorLogic,
                       logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                     ): Any

  def evalOperator(
                     conditions: Array[JsonLogicCore],
                     conditionCaller: ComposeLogic,
                     reduceLogic: EvaluatorLogic,
                     logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                  ): Any = {
    checkInputs(conditions)
    val arrValues = reduceLogic.evaluate(conditions(0), logicOperatorToValue).asInstanceOf[Array[Any]]
    val logicArr = conditions.slice(1, conditions.length)
    composeOperator(arrValues, logicArr, conditionCaller, reduceLogic, logicOperatorToValue)
  }

  def checkInputs(conditions: Array[JsonLogicCore]): Unit

}
