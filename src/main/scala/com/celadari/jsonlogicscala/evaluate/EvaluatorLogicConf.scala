// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate

import java.util.Properties
import scala.reflect.runtime.{universe => ru}
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.{MissingAccessorException, ObjectRecipe}
import play.api.libs.json.Json
import com.celadari.jsonlogicscala.configuration.ConfigurationFetcher
import com.celadari.jsonlogicscala.exceptions.ConfigurationException
import com.celadari.jsonlogicscala.tree.types.TypeValue
import com.celadari.jsonlogicscala.evaluate.defaults._
import com.celadari.jsonlogicscala.converters.CollectionConverters.MapHasAsScala


/**
 * Companion object to hold implicit: [[com.celadari.jsonlogicscala.evaluate.EvaluatorLogicConf]], mapping of default
 * operators (type_codename -> method_conf), and method to create a custom configuration.
 */
object EvaluatorLogicConf {

  // scalastyle:off null
  val DEFAULT_REDUCE_OPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
    "<" -> ("$less", OperatorLess),
    "<=" -> ("$less$eq", OperatorLessEq),
    ">" -> ("$greater", OperatorGreater),
    ">=" -> ("$greater$eq", OperatorGreaterEq),
    "<<" -> ("$less$less", OperatorLeftShiftBitwise),
    ">>" -> ("$greater$greater", OperatorRightShiftBitwise),
    ">>>" -> ("$greater$greater$greater", OperatorRightShiftZeroFillerBitwise),
    "+" -> ("$plus", OperatorPlus),
    "-" -> ("$minus", OperatorMinus),
    "*" -> ("$times", OperatorTimes),
    "**" -> ("$times$times", OperatorPow),
    "/" -> ("$div", OperatorDiv),
    "%" -> ("$percent", OperatorModulo),
    "^" -> ("$up", OperatorXorBitwise),
    "|" -> ("$bar", OperatorOrBitwise),
    "or" -> ("$bar$bar", OperatorOr),
    "&" -> ("$amp", OperatorAndBitwise),
    "and" -> ("$amp$amp", OperatorAnd),
    "==" -> ("$eq$eq", OperatorEqWithTypeCoercion),
    "===" -> ("$eq$eq$eq", OperatorEqStrict),
    "!=" -> ("$bang$eq", OperatorNEqWithTypeCoercion),
    "!==" -> ("$bang$eq$eq", OperatorNEqStrict),
    "max" -> ("max", OperatorMax),
    "min" -> ("min", OperatorMin)
  )

  /**
   * Maps default Reduce operators (operator_codename -> method_conf).
   */
  val DEFAULT_REDUCE_METHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCE_OPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator)
      )
    }}

  val DEFAULT_NON_REDUCE_OPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
    "if" -> ("ifElse", OperatorIfElse),
    "in" -> ("in", OperatorIn),
    "merge" -> ("merge", OperatorMerge),
    "cat" -> ("cat", OperatorCat),
    "substr" -> ("substr", OperatorSubstr),
    "at" -> ("at", OperatorAt)
  )

  /**
   * Maps default Non-Reduce operators (operator_codename -> method_conf).
   */
  val DEFAULT_NON_REDUCE_OPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_NON_REDUCE_OPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator),
        isReduceTypeOperator = false
      )
    }}

  val DEFAULT_COMPOSITION_OPERATORS_TO_METHOD_NAME: Map[String, Operator] = Map(
    "map" -> OperatorMap,
    "reduce" -> OperatorReduce,
    "filter" -> OperatorFilter,
    "all" -> OperatorAll,
    "none" -> OperatorNone,
    "some" -> OperatorSome
  )

  /**
   * Maps default Composition operators (operator_codename -> method_conf).
   */
  val DEFAULT_COMPOSITION_OPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_COMPOSITION_OPERATORS_TO_METHOD_NAME
    .map{case (operator, objOperator) => {
      operator -> MethodConf(
        operator,
        null,
        Some(objOperator),
        isReduceTypeOperator = false,
        isCompositionOperator = true
      )
    }}

  val DEFAULT_UNARY_OPERATORS_TO_METHOD_NAME: Map[String, Operator] = Map(
    "!" -> OperatorNeg,
    "get_or_default_array" -> OperatorOptionGetOrDefaultArray,
    "get_or_default_boolean" -> OperatorOptionGetOrDefaultBoolean,
    "get_or_default_byte" -> OperatorOptionGetOrDefaultByte,
    "get_or_default_short" -> OperatorOptionGetOrDefaultShort,
    "get_or_default_int" -> OperatorOptionGetOrDefaultInt,
    "get_or_default_long" -> OperatorOptionGetOrDefaultLong,
    "get_or_default_float" -> OperatorOptionGetOrDefaultFloat,
    "get_or_default_double" -> OperatorOptionGetOrDefaultDouble,
    "get_or_default_string" -> OperatorOptionGetOrDefaultString,
    "get_or_default_map" -> OperatorOptionGetOrDefaultMap,
    "get_value_or_null" -> OperatorOptionGetValueOrNull
  )

  /**
   * Maps default Unary operators (operator_codename -> method_conf).
   */
  val DEFAULT_UNARY_OPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_UNARY_OPERATORS_TO_METHOD_NAME
    .map{case (operator, objOperator) => {
      operator -> MethodConf(
        operator,
        null,
        Some(objOperator),
        isReduceTypeOperator = false,
        isUnaryOperator = true
      )
    }}

  /**
   * Maps default operators (operator_codename -> method_conf).
   */
  val DEFAULT_METHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCE_METHOD_CONFS ++
    DEFAULT_NON_REDUCE_OPERATORS_CONFS ++ DEFAULT_COMPOSITION_OPERATORS_CONFS ++ DEFAULT_UNARY_OPERATORS_CONFS

  /**
   * Returns tuple of (typeCodename, method_conf) from configuration file.
   * @param fileName: path of configuration file.
   * @param prop: read properties from configuration file.
   * @param operatorToOwnerManualAdd: map of (operator_codename -> operator) for manually added operators.
   * @return tuple (operator_codename, method_conf) after reading configuration.
   */
  // scalastyle:off return
  def createConfMethod(
                        fileName: String,
                        prop: Properties,
                        operatorToOwnerManualAdd: Map[String, Operator]
                      ): (String, MethodConf) = {
    if (!prop.containsKey("operator")) throw new ConfigurationException(s"Property file '$fileName' must define key 'operator'")
    if (!prop.containsKey("methodName")) throw new ConfigurationException(s"Property file '$fileName' must define key 'methodName'")
    val operator = prop.remove("operator").toString
    val methodName = prop.remove("methodName").toString

    val isReduceTypeOperator = ConfigurationFetcher.getOptionalBooleanProperty("isReduceTypeOperator", defaultValue = true, prop, {
      new ConfigurationException(s"Property 'isReduceTypeOperator' in property file '$fileName' is not a valid boolean parameter")
    })
    val isCompositionOperator = ConfigurationFetcher.getOptionalBooleanProperty("isCompositionOperator", false, prop, {
      new ConfigurationException(s"Property 'isCompositionOperator' in property file '$fileName' is not a valid boolean parameter")
    })
    val isUnaryOperator = ConfigurationFetcher.getOptionalBooleanProperty("isUnaryOperator", false, prop, {
      new ConfigurationException(s"Property 'isUnaryOperator' in property file '$fileName' is not a valid boolean parameter")
    })
    val ownerMethodOptManualAdd = operatorToOwnerManualAdd.get(operator)

    val m = ru.runtimeMirror(getClass.getClassLoader)

    if (ownerMethodOptManualAdd.isDefined) {
      return (operator, MethodConf(operator, methodName, ownerMethodOptManualAdd, isReduceTypeOperator, isCompositionOperator, isUnaryOperator))
    }

    if (!prop.containsKey("ownerMethod")) {
      return (operator, MethodConf(operator, methodName, None, isReduceTypeOperator, isCompositionOperator, isUnaryOperator))
    }

    val isObject = ConfigurationFetcher.getOptionalBooleanProperty("singleton", defaultValue = false, prop, {
      new ConfigurationException(s"Property 'singleton' in property file '$fileName' is not a valid boolean parameter")
    })
    val className = prop.remove("ownerMethod").toString

    if (isObject) {
      try {
        val instance = m.reflectModule(m.staticModule(className)).instance.asInstanceOf[Operator]
        (operator, MethodConf(operator, methodName, Some(instance), isReduceTypeOperator, isCompositionOperator, isUnaryOperator))
      }
      catch {
        case castException: java.lang.ClassCastException => {
          throw new ConfigurationException(s"Found object is not 'com.celadari.jsonlogicscala.evaluate.Operator' type:\n${castException.getMessage}")
        }
        case _: Throwable => {
          throw new ConfigurationException(s"No singleton object found for: '$className'\nCheck if 'ownerMethod' '$className' is correct and if 'singleton'" +
            s" property in '$fileName' property file is correct")
        }
      }
    }
    else {
      try {
        val objectRecipe = new ObjectRecipe(className)
        val sep = if (prop.containsKey("sep")) prop.remove("sep").toString else ";"
        if (prop.containsKey("constructorArgNames")) objectRecipe.setConstructorArgNames(prop.remove("constructorArgNames").toString.split(sep))
        objectRecipe.setAllProperties(prop)
        val instance = objectRecipe.create().asInstanceOf[Operator]
        (operator, MethodConf(operator, methodName, Some(instance), isReduceTypeOperator, isCompositionOperator, isUnaryOperator))
      }
      catch {
        case castException: java.lang.ClassCastException => {
          throw new ConfigurationException(s"Found class not 'com.celadari.jsonlogicscala.evaluate.Operator' instance:\n${castException.getMessage}")
        }
        case _: MissingAccessorException => {
          throw new ConfigurationException(s"Field error, check that no field in '$className' is missing in '$fileName' property file.\nCheck that no" +
            s" property in '$fileName' file is not undefined in '$className' class.\nCheck if '$className' class constructor requires arguments or if" +
            s" argument names defined in '$fileName' property file are correct")
        }
        case _: Throwable => {
          throw new ConfigurationException(s"No class found for: '$className'\nCheck if 'ownerMethod' '$className' is correct and if 'singleton'" +
            s" property in '$fileName' property file is correct")
        }
      }
    }
  }

  /**
   * Returns a custom evaluator configuration [[com.celadari.jsonlogicscala.evaluate.EvaluatorLogicConf]].
   * @param pathEvaluatorValueLogic: folder to [[com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic]] provider-configuration files.
   * @param pathOperator: folder to [[com.celadari.jsonlogicscala.evaluate.Operator]] provider-configuration files.
   * @param methodConfsManualAdd: map of (type_codename -> method_conf) to be added to the evaluator configuration.
   * @param operatorToOwnerManualAdd: map of (type_codename -> operator) to be added to the evaluator configuration.
   * @param evaluatorValueLogicManualAdd: map of (type -> evaluator_value_logic) to be added to the evaluator configuration.
   * @param isPriorityToManualAdd: boolean indicating if manual add has priority over provider-configuration folder add.
   * @return custom serializer configuration.
   */
  def createConf(
                  pathEvaluatorValueLogic: String = "META-INF/services/",
                  pathOperator: String = "META-INF/services/",
                  methodConfsManualAdd: Map[String, MethodConf] = DEFAULT_METHOD_CONFS,
                  operatorToOwnerManualAdd: Map[String, Operator] = Map(),
                  evaluatorValueLogicManualAdd: Map[TypeValue, EvaluatorValueLogic] = Map(),
                  isPriorityToManualAdd: Boolean = true
                ): EvaluatorLogicConf = {

    val finderEvaluatorValueLogic = new ResourceFinder(pathEvaluatorValueLogic)
    val propsEvaluatorValueLogic = finderEvaluatorValueLogic.mapAllProperties(classOf[EvaluatorValueLogic].getName).asScala
    val evaluatorValueLogicMetaInfTypeNonParsed = propsEvaluatorValueLogic
      .map{case (fileName, prop) => ConfigurationFetcher.getOrCreateClassFromProperties[EvaluatorValueLogic](fileName, prop)}
      .toMap
    val evaluatorValueLogicMetaInf = evaluatorValueLogicMetaInfTypeNonParsed
      .map{case (typeNonParsed, evaluator) => (Json.parse(typeNonParsed).as[TypeValue], evaluator)}

    val finderMethodConf = new ResourceFinder(pathOperator)
    val propsMethodConf = finderMethodConf.mapAllProperties(classOf[MethodConf].getName).asScala
    val methodConfMetaInf = propsMethodConf.map{case (fileName, prop) => createConfMethod(fileName, prop, operatorToOwnerManualAdd)}.toMap

    EvaluatorLogicConf(methodConfMetaInf, methodConfsManualAdd, evaluatorValueLogicMetaInf, evaluatorValueLogicManualAdd, isPriorityToManualAdd)
  }

  implicit val implReduceLogicConf: EvaluatorLogicConf = createConf()
}

/**
 * Represents an evaluator's configuration.
 * It informs the evaluator how to map an operator_codename to an actual method.
 * @param operatorToMethodConfMetaInfAdd: map of (type_codename -> method_conf) fetched from META-INF resources folder.
 * @param operatorToMethodConfManualAdd: map of (type_codename -> method_conf) manually added.
 * @param valueLogicTypeToReducerMetaInfAdd: map of (type -> evaluator_value_logic) fetched from META-INF resources folder.
 * @param valueLogicTypeToReducerManualAdd: map of (type -> evaluator_value_logic) manually added.
 * @param isPriorityToManualAdd: boolean indicating if manual add has priority over provider-configuration folder add.
 */
case class EvaluatorLogicConf(
                               operatorToMethodConfMetaInfAdd: Map[String, MethodConf],
                               operatorToMethodConfManualAdd: Map[String, MethodConf],
                               valueLogicTypeToReducerMetaInfAdd: Map[TypeValue, EvaluatorValueLogic],
                               valueLogicTypeToReducerManualAdd: Map[TypeValue, EvaluatorValueLogic],
                               isPriorityToManualAdd: Boolean = true
                             ) {

  /**
   * Maps codename_operator to [[com.celadari.jsonlogicscala.evaluate.MethodConf]].
   * If "isPriorityToManualAdd" is true, if a codename_operator is defined in both "operatorToMethodConfMetaInfAdd"
   * and "operatorToMethodConfManualAdd" then manual add is selected, otherwise fetched META-INF is selected.
   */
  val operatorToMethodConf: Map[String, MethodConf] = if (isPriorityToManualAdd) operatorToMethodConfMetaInfAdd ++ operatorToMethodConfManualAdd
                                                      else operatorToMethodConfManualAdd ++ operatorToMethodConfMetaInfAdd

  /**
   * Maps type to [[com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic]].
   * If "isPriorityToManualAdd" is true, if a type is defined in both "valueLogicTypeToReducerMetaInfAdd"
   * and "valueLogicTypeToReducerManualAdd" then manual add is selected, otherwise fetched META-INF is selected.
   */
  val valueLogicTypeToReducer: Map[TypeValue, EvaluatorValueLogic] =
    if (isPriorityToManualAdd) valueLogicTypeToReducerMetaInfAdd ++ valueLogicTypeToReducerManualAdd
    else valueLogicTypeToReducerManualAdd ++ valueLogicTypeToReducerMetaInfAdd
}
