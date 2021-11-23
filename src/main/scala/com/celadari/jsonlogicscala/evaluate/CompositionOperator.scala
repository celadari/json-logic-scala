// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate

import com.celadari.jsonlogicscala.exceptions.{EvaluateException, EvaluationException, IllegalInputException}
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}


/**
 * Companion object containing class [[com.celadari.jsonlogicscala.evaluate.CompositionOperator.ComposeJsonLogicCore]]'s
 * definition.
 */
object CompositionOperator {

  /**
   * Class for recursively traversing [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] data structure and replace
   * any [[com.celadari.jsonlogicscala.tree.ValueLogic]] object referring to a variable in a
   * [[com.celadari.jsonlogicscala.evaluate.CompositionOperator]] operator by a
   * [[com.celadari.jsonlogicscala.tree.VariableLogic]] object.
   * This is performed before any evaluation.
   * @param jsonLogicVariable: syntax tree to be traversed.
   * @param conf: informs of mappings (codename_operator -> method_conf) and mappings (type -> evaluator_value_logic).
   */
  class ComposeJsonLogicCore(protected[this] val jsonLogicVariable: JsonLogicCore)(implicit val conf: EvaluatorLogicConf) {
    private[this] var jsonLogicTraversed: JsonLogicCore = _
    private[this] var isEvaluated: Boolean = false

    /**
     * Traverses [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] recursively data structure and replace
     * any [[com.celadari.jsonlogicscala.tree.ValueLogic]] object referring to a variable in a
     * [[com.celadari.jsonlogicscala.evaluate.CompositionOperator]] operator by a
     * [[com.celadari.jsonlogicscala.tree.VariableLogic]] object.
     * @param jsonLogicCore: current traversed node.
     * @param parentComposeLogicOperatorOpt: last traversed composition operator.
     * @return [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] with
     *         [[com.celadari.jsonlogicscala.tree.VariableLogic]] for nodes representing variable from composition operators.
     */
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

    /**
     * Returns recursively traversed [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] and whose
     * [[com.celadari.jsonlogicscala.tree.ValueLogic]] objects referring to variables in
     * [[com.celadari.jsonlogicscala.evaluate.CompositionOperator]] operator have been replaced by
     * [[com.celadari.jsonlogicscala.tree.VariableLogic]].
     * If tree has not yet been traversed, it is traversed, otherwise returns cached value.
     * @return ready for evaluation [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] object.
     */
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

/**
 * Defines interface of class/object that defines a composition operator.
 * Unlike other operators, these operators are more complex and new to inform sub-nodes in syntax tree of values before
 * evaluating its current node. This is performed by updating the map "logicOperatorToValue" which is passed on to sub-nodes.
 * Invoked method has to be "composeOperator" and overloading this method doesn't work.
 * @example map, filter, all, none, some
 */
trait CompositionOperator extends Operator {

  /**
   * Returns value by this composition operator.
   * @param values: values operator operates on.
   * @param logicArr: array of sub-nodes this composition operator operates one. Most of the time array is of length 1.
   * @param conditionCaller: node in syntax tree whose operator is this one.
   * @param reduceLogic: evaluator that called this method. Required to pass updated "logicOperatorToValue" recursively
   * to sub-tree and evaluate it.
   * @param logicOperatorToValue: map of (composition_operator -> map(variable_name -> variable_value)).
   * @return operated value by operator.
   */
  def composeOperator(
                       values: Array[Any],
                       logicArr: Array[JsonLogicCore],
                       conditionCaller: ComposeLogic,
                       reduceLogic: EvaluatorLogic,
                       logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                     ): Any

  /**
   * Returns evaluated result of corresponding condition (internal node in syntax tree).
   * This method carries a map of map of (composition_operator -> map(variable_name -> variable_value)) to fetch
   * the value of variable (depends on the composition_operator and the values this composition_operator is applied to).
   * This map of map is necessary as a composition_operator can be composed with another composition_operator which requires
   * to be able to distinguish between values which composition_operator they belong to.
   * @param conditionCaller: node in syntax tree to evaluate.
   * @param reduceLogic: evaluator that called this method. Required to pass updated "logicOperatorToValue" recursively
   * to sub-tree and evaluate it.
   * @param logicOperatorToValue: map of (composition_operator -> map(variable_name -> variable_value)).
   * @return evaluated result.
   */
  def evalOperator(
                     conditionCaller: ComposeLogic,
                     reduceLogic: EvaluatorLogic,
                     logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                  ): Any = {
    val conditions = conditionCaller.conditions
    checkInputs(conditions)
    val arrValues = reduceLogic.evaluate(conditions(0), logicOperatorToValue).asInstanceOf[Array[Any]]
    val logicArr = conditions.slice(1, conditions.length)
    composeOperator(arrValues, logicArr, conditionCaller, reduceLogic, logicOperatorToValue)
  }

  /**
   * Checks if conditions satisfies this operator's constraints.
   * Should throw an exception if not.
   * @param conditions: set of sub-nodes to check validity.
   */
  def checkInputs(conditions: Array[JsonLogicCore]): Unit

}
