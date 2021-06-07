package com.github.celadari.jsonlogicscala.evaluate

import java.util.Properties
import scala.reflect.runtime.{universe => ru}
import org.apache.xbean.finder.ResourceFinder
import org.apache.xbean.recipe.{MissingAccessorException, ObjectRecipe}
import play.api.libs.json.Json
import com.github.celadari.jsonlogicscala.tree.types.TypeValue
import com.github.celadari.jsonlogicscala.evaluate.defaults._
import com.github.celadari.jsonlogicscala.configuration.ConfigurationFetcher
import com.github.celadari.jsonlogicscala.exceptions.ConfigurationException
import com.github.celadari.jsonlogicscala.converters.CollectionConverters.MapHasAsScala


object EvaluatorLogicConf {

  // scalastyle:off null
  val DEFAULT_REDUCEOPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
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
  val DEFAULT_REDUCEMETHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCEOPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator)
      )
    }}

  val DEFAULT_NONREDUCEOPERATORS_TO_METHODNAME: Map[String, (String, Operator)] = Map(
    "if" -> ("ifElse", OperatorIfElse),
    "in" -> ("in", OperatorIn),
    "merge" -> ("merge", OperatorMerge),
    "cat" -> ("cat", OperatorCat),
    "substr" -> ("substr", OperatorSubstr),
    "at" -> ("at", OperatorAt)
  )
  val DEFAULT_NONREDUCEOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_NONREDUCEOPERATORS_TO_METHODNAME
    .map{case (operator, (methodName, objOperator)) => {
      operator -> MethodConf(
        operator,
        methodName,
        Some(objOperator),
        isReduceTypeOperator = false
      )
    }}

  val DEFAULT_COMPOSITIONOPERATORS_TO_METHODNAME: Map[String, Operator] = Map(
    "map" -> OperatorMap,
    "reduce" -> OperatorReduce,
    "filter" -> OperatorFilter,
    "all" -> OperatorAll,
    "none" -> OperatorNone,
    "some" -> OperatorSome
  )
  val DEFAULT_COMPOSITIONOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_COMPOSITIONOPERATORS_TO_METHODNAME
    .map{case (operator, objOperator) => {
      operator -> MethodConf(
        operator,
        null,
        Some(objOperator),
        isReduceTypeOperator = false,
        isCompositionOperator = true
      )
    }}
  val DEFAULT_UNARYOPERATORS_TO_METHODNAME: Map[String, Operator] = Map(
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
  val DEFAULT_UNARYOPERATORS_CONFS: Map[String, MethodConf] = DEFAULT_UNARYOPERATORS_TO_METHODNAME
    .map{case (operator, objOperator) => {
      operator -> MethodConf(
        operator,
        null,
        Some(objOperator),
        isReduceTypeOperator = false,
        isUnaryOperator = true
      )
    }}

  val DEFAULT_METHOD_CONFS: Map[String, MethodConf] = DEFAULT_REDUCEMETHOD_CONFS ++
    DEFAULT_NONREDUCEOPERATORS_CONFS ++ DEFAULT_COMPOSITIONOPERATORS_CONFS ++ DEFAULT_UNARYOPERATORS_CONFS

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

    val isObject = ConfigurationFetcher.getOptionalBooleanProperty("singleton", defaultValue= false, prop, {
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
          throw new ConfigurationException(s"Found object is not 'com.github.celadari.jsonlogicscala.evaluate.Operator' type:\n${castException.getMessage}")
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
          throw new ConfigurationException(s"Found class not 'com.github.celadari.jsonlogicscala.evaluate.Operator' instance:\n${castException.getMessage}")
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

  def createConf(
                  pathEvaluatorLogic: String = "META-INF/services/",
                  pathOperator: String = "META-INF/services/",
                  methodConfsManualAdd: Map[String, MethodConf] = DEFAULT_METHOD_CONFS,
                  evaluatorValueLogicManualAdd: Map[TypeValue, EvaluatorValueLogic] = Map(),
                  operatorToOwnerManualAdd: Map[String, Operator] = Map(),
                  isPriorityToManualAdd: Boolean = true
                ): EvaluatorLogicConf = {

    val finderEvaluatorValueLogic = new ResourceFinder(pathEvaluatorLogic)
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

case class EvaluatorLogicConf(
                               operatorToMethodConfMetaInfAdd: Map[String, MethodConf],
                               operatorToMethodConfManualAdd: Map[String, MethodConf],
                               valueLogicTypeToReducerMetaInfAdd: Map[TypeValue, EvaluatorValueLogic],
                               valueLogicTypeToReducerManualAdd: Map[TypeValue, EvaluatorValueLogic],
                               isPriorityToManualAdd: Boolean = true
                             ) {

  val operatorToMethodConf: Map[String, MethodConf] = if (isPriorityToManualAdd) operatorToMethodConfMetaInfAdd ++ operatorToMethodConfManualAdd
                                                      else operatorToMethodConfManualAdd ++ operatorToMethodConfMetaInfAdd
  val valueLogicTypeToReducer: Map[TypeValue, EvaluatorValueLogic] =
    if (isPriorityToManualAdd) valueLogicTypeToReducerMetaInfAdd ++ valueLogicTypeToReducerManualAdd
    else valueLogicTypeToReducerManualAdd ++ valueLogicTypeToReducerMetaInfAdd
}
