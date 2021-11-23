// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate

import java.lang.reflect.InvocationTargetException
import com.celadari.jsonlogicscala.evaluate.CompositionOperator.ComposeJsonLogicCore
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, EvaluationException, IllegalInputException, IncompatibleMethodsException}


/**
 * Responsible for evaluating scala data structure [[com.celadari.jsonlogicscala.tree.JsonLogicCore]].
 * May be extended to fit custom use cases. Providing the right configuration via
 * [[com.celadari.jsonlogicscala.evaluate.EvaluatorLogicConf]] is enough to cover most cases.
 * You may redefine methods to handle extreme uncommon cases.
 * @param conf: informs of mappings (codename_operator -> method_conf) and mappings (type -> evaluator_value_logic).
 */
class EvaluatorLogic(implicit val conf: EvaluatorLogicConf) {

  /**
   * Returns evaluated result of corresponding condition (internal node in syntax tree).
   * This method carries a map of map of (composition_operator -> map(variable_name -> variable_value)) to fetch
   * the value of variable (depends on the composition_operator and the values this composition_operator is applied to).
   * @param condition: node in syntax tree to evaluate.
   * @param logicToValue: map of (composition_operator -> map(variable_name -> variable_value))
   * @return evaluated result.
   */
  // scalastyle:off return
  protected[this] def evaluateComposeLogic(condition: ComposeLogic, logicToValue: Map[ComposeLogic, Map[String, Any]]): Any = {
    val operator = condition.operator
    val confMethod = conf.operatorToMethodConf.getOrElse(operator, {
      throw new IllegalInputException(s"No configuration for operator: $operator")
    })

    // unary operators
    if (confMethod.isUnaryOperator) {
      val value = evaluate(condition.conditions(0), logicToValue)
      return confMethod.ownerMethodOpt.get.asInstanceOf[UnaryOperator].unaryOperator(value)
    }

    // Composition operators: map, filter, reduce
    if (confMethod.isCompositionOperator) {
      return confMethod.ownerMethodOpt.get.asInstanceOf[CompositionOperator]
        .evalOperator(condition, this, logicToValue)
    }

    val conditionsEval = condition.conditions.map(cond => evaluate(cond, logicToValue))

    // Global operators: ifElse, merge, in
    if (!confMethod.isReduceTypeOperator) {
      val methods = confMethod.ownerMethodOpt.get.getClass.getMethods.filter(_.getName == confMethod.methodName).toSet
      val (paramTypes, conditionsEvalCasted) = MethodSignatureFinder.minsSupAndCastedValues(conditionsEval, methods.map(_.getParameterTypes.apply(0)))

      if (paramTypes.isEmpty) {
        throw new IncompatibleMethodsException(s"Method ${confMethod.methodName} doesn't accept parameter of type: ${conditionsEval.getClass}")
      }

      val method = methods.find(_.getParameterTypes.apply(0) == paramTypes.head).get

      return method.invoke(confMethod.ownerMethodOpt.get, conditionsEvalCasted)
    }

    val paths = new MethodSignatureFinder(conditionsEval, confMethod).findPaths()
    if (paths.isEmpty) {
      throw new IncompatibleMethodsException(s"Method ${confMethod.methodName} doesn't accept parameter of type: ${conditionsEval.getClass}")
    }
    val methodsAndIsOwned = paths.head

    methodsAndIsOwned
      .zip(conditionsEval.tail)
      .foldLeft[Any](conditionsEval.head){case (conditionEval1, ((method, isMethodOwnedByReducedValue), conditionEval2)) => {
        if (isMethodOwnedByReducedValue) {
          method.invoke(conditionEval1, conditionEval1.asInstanceOf[java.lang.Object], conditionEval2.asInstanceOf[java.lang.Object])
        }
        else method.invoke(confMethod.ownerMethodOpt.get, conditionEval1.asInstanceOf[java.lang.Object], conditionEval2.asInstanceOf[java.lang.Object])
      }}
  }

  /**
   * Returns transformed value of leaf node in syntax tree before evaluation.
   * @param condition: leaf node whose value is to be transformed.
   * @return transformed value.
   */
  // scalastyle:off return null
  protected[this] def evaluateValueLogic(condition: ValueLogic[_]): Any = {
    val value = condition.valueOpt.get
    val typeValueOpt = condition.typeOpt
    if (typeValueOpt.isEmpty) return null

    val evaluatorClassOpt = conf.valueLogicTypeToReducer.get(typeValueOpt.get)
    evaluatorClassOpt
      .getOrElse(EvaluatorValueLogic.IDENTITY_REDUCER_VALUE_LOGIC)
      .evaluateValueLogic(value)
  }

  /**
   * Returns evaluated result of corresponding condition (node in syntax tree).
   * This method carries a map of map of (composition_operator -> map(variable_name -> variable_value)) to fetch
   * the value of variable (depends on the composition_operator and the values this composition_operator is applied to).
   * @param condition: node in syntax tree to evaluate.
   * @param logicOperatorToValue: map of (composition_operator -> map(variable_name -> variable_value))
   * @return evaluated result.
   */
  def evaluate(condition: JsonLogicCore, logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]): Any = {
    try {
      condition match {
        case composeLogic: ComposeLogic => evaluateComposeLogic(composeLogic, logicOperatorToValue)
        case valueLogic: ValueLogic[_] => evaluateValueLogic(valueLogic)
        case VariableLogic(variableName, composeOperator) => logicOperatorToValue(composeOperator)(variableName)
      }
    }
    catch {
      case invTargetException: InvocationTargetException => {
        invTargetException.getTargetException match {
          case evaluationException: EvaluationException => throw evaluationException
          case throwable: Throwable => throw new EvaluationException(throwable.getMessage, condition, throwable)
        }
      }
      case evaluationException: EvaluationException => throw evaluationException
      case throwable: Throwable => throw new EvaluationException(throwable.getMessage, condition, throwable)
    }
  }

  /**
   * Returns evaluated result of scala data structure [[com.celadari.jsonlogicscala.tree.JsonLogicCore]].
   * A [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] is a scala data structure that represents a json-logic-typed.
   * A json-logic-typed object represents an expression.
   * This method evaluates the underlying expression represented by a scala data structure
   * [[com.celadari.jsonlogicscala.tree.JsonLogicCore]].
   * Before evaluation, the underlying syntax tree is traversed
   * - using [[com.celadari.jsonlogicscala.evaluate.CompositionOperator.ComposeJsonLogicCore]] - and
   * any [[com.celadari.jsonlogicscala.tree.ValueLogic]] object representing a variable
   * (of a [[com.celadari.jsonlogicscala.evaluate.CompositionOperator]]) is replaced by an
   * [[com.celadari.jsonlogicscala.tree.VariableLogic]] object recursively.
   * @param jsonLogicCore: expression to evaluate.
   * @return evaluated result.
   */
  // scalastyle:off null
  def eval(jsonLogicCore: JsonLogicCore): Any = {
    var composedLogic: JsonLogicCore = null
    try {
      composedLogic = new ComposeJsonLogicCore(jsonLogicCore).evaluate()
      val logicOperatorToValue: Map[ComposeLogic, Map[String, Any]] = Map()
      evaluate(composedLogic, logicOperatorToValue)
    }
    catch {
      case EvaluationException(msg, condition, origExcept) => throw new EvaluateException(msg, condition, composedLogic, origExcept)
      case evaluateException: EvaluateException => throw evaluateException
    }
  }

}
