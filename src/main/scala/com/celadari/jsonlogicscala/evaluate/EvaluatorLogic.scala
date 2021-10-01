package com.celadari.jsonlogicscala.evaluate

import java.lang.reflect.InvocationTargetException
import CompositionOperator.ComposeJsonLogicCore
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import com.celadari.jsonlogicscala.exceptions.{EvaluateException, EvaluationException, IllegalInputException, IncompatibleMethodsException}


class EvaluatorLogic(implicit val conf: EvaluatorLogicConf) {

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
        .evalOperator(condition.conditions, condition, this, logicToValue)
    }

    val conditionsEval = condition.conditions.map(cond => evaluate(cond, logicToValue))

    // Global operators: ifElse, merge, in
    if (!confMethod.isReduceTypeOperator) {
      val methods = confMethod.ownerMethodOpt.get.getClass.getMethods.filter(_.getName == confMethod.methodName).toSet
      val (paramTypes, conditionsEvalCasted) = MethodSignatureFinder.maxMinsAndCastedValues(conditionsEval, methods.map(_.getParameterTypes.apply(0)))

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

  // scalastyle:off return null
  protected[this] def evaluateValueLogic(condition: ValueLogic[_]): Any = {
    val value = condition.valueOpt.get
    val typeValueOpt = condition.typeOpt
    if (typeValueOpt.isEmpty) return null

    val evaluatorClassOpt = conf.valueLogicTypeToReducer.get(typeValueOpt.get)
    evaluatorClassOpt
      .getOrElse(EvaluatorValueLogic.IDENTITY_REDUCER_VALUELOGIC)
      .evaluateValueLogic(value)
  }

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
