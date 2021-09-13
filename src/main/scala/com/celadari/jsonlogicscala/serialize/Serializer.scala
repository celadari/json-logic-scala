// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize

import play.api.libs.json._
import com.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, OptionTypeValue, SimpleTypeValue, TypeValue}
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import com.celadari.jsonlogicscala.converters.CollectionConverters.HasMapValues
import com.celadari.jsonlogicscala.exceptions.{IllegalInputException, InvalidValueLogicException}


/**
 * Companion object holding implicit serializer.
 * Useful to invoke methods using implicit [[com.celadari.jsonlogicscala.serialize.Serializer]] that do not require
 * custom Serializer.
 */
object Serializer {
  implicit val defaultSerializer: Serializer = new Serializer()
}

/**
 * Responsible for serializing scala data structure [[com.celadari.jsonlogicscala.tree.JsonLogicCore]] to json.
 * May be extended to fit custom use cases. Providing the right configuration via
 * [[com.celadari.jsonlogicscala.serialize.SerializerConf]] is enough to cover most cases.
 * You may redefine methods to handle extreme uncommon cases.
 * @param conf: informs of mappings (type_codename -> marshaller).
 */
class Serializer(implicit val conf: SerializerConf) {
  /**
   * Maps type_codename to [[com.celadari.jsonlogicscala.serialize.Marshaller]].
   * @note More specifically, keys should be type_codename of [[com.celadari.jsonlogicscala.tree.types.SimpleTypeValue]]
   * as generic types (OptionTypeValue, MapTypeValue, ArrayTypeValue) are handled recursively by getMarshaller.
   */
  protected[this] val marshallers: Map[String, Marshaller] = if (conf.isPriorityToManualAdd) conf.marshallerMetaInfAdd ++ conf.marshallersManualAdd
    else conf.marshallersManualAdd ++ conf.marshallerMetaInfAdd

  /**
   * Returns [[com.celadari.jsonlogicscala.serialize.Marshaller]] associated with input typeValue.
   * If input typeValue is [[com.celadari.jsonlogicscala.tree.types.SimpleTypeValue]] then returns mapped value
   * by marshallers attribute.
   * If input typeValue is [[com.celadari.jsonlogicscala.tree.types.OptionTypeValue]],
   * [[com.celadari.jsonlogicscala.tree.types.ArrayTypeValue]], [[com.celadari.jsonlogicscala.tree.types.MapTypeValue]]
   * then a new [[com.celadari.jsonlogicscala.serialize.Marshaller]] is recursively created by checking paramType of
   * input typeValue.
   * @param typeValue: input type to return associated Marshaller.
   * @return Marshaller associated to typeValue.
   */
  protected[this] def getMarshaller(typeValue: TypeValue): Marshaller = {
    typeValue match {
      case SimpleTypeValue(codename) => marshallers.getOrElse(codename, throw new IllegalInputException(s"No marshaller defined for $codename"))
      case ArrayTypeValue(paramType) => new Marshaller {
        override def marshal(value: Any): JsValue = {
          value match {
            case arr: Array[_] => JsArray(arr.map(el => getMarshaller(paramType).marshal(el)))
            case other => {
              throw new IllegalInputException(s"Illegal input argument to ArrayType Marshaller: $other." +
                "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
            }
          }
        }
      }
      case MapTypeValue(paramType) => new Marshaller {
        override def marshal(value: Any): JsValue = {
          value match {
            case map: Map[_, _] => {
              if (!map.keySet.forall(_.isInstanceOf[String])) {
                throw new IllegalInputException(s"Map keys must be of type String to be serialized.\nCurrent type: $map")
              }
              JsObject(map.asInstanceOf[Map[String, Any]].mapValuesAccordingToScalaVersion(key => getMarshaller(paramType).marshal(key)))
            }
            case other => {
              throw new IllegalInputException(s"Illegal input argument to MapType Marshaller: $other." +
                "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
            }
          }

        }
      }
      case OptionTypeValue(paramType) => new Marshaller {
        override def marshal(value: Any): JsValue = {
          value match {
            case opt: Option[_] => opt.map(el => getMarshaller(paramType).marshal(el)).getOrElse(JsNull)
            case other => {
              throw new IllegalInputException(s"Illegal input argument to OptionType Marshaller: $other." +
                "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
            }
          }
        }
      }
      case AnyTypeValue => {
        throw new IllegalInputException("Cannot serialize JsonLogicCore object " +
          "with type AnyTypeValue. \nAnyTypeValue is used at evaluation only for composition operators")
      }
      case _ => throw new IllegalInputException("Illegal argument type value")
    }
  }

  /**
   * Returns tuple of serialized (logic, data) of scala data structure [[com.celadari.jsonlogicscala.tree.ValueLogic]].
   * Json-logic-typed format requires logic and data to split.
   * @param valueLogic: input ValueLogic to be split into (logic JsValue, data JsValue).
   * @return (logic JsValue, data JsValue)
   */
  // scalastyle:off return
  protected[this] def serializeValueLogic(valueLogic: ValueLogic[_]): (JsValue, JsValue) = {

    if (valueLogic.variableNameOpt.isDefined) return (JsObject(Map("var" -> JsString(valueLogic.variableNameOpt.get))), JsObject(Map[String, JsString]()))

    val (jsType, jsonLogicDatum) = valueLogic
      .valueOpt
      .map(value => {
        val typeCodenameOpt = valueLogic.typeCodenameOpt
        if (typeCodenameOpt.isEmpty) throw new InvalidValueLogicException("ValueLogic with defined value must define a typeCodename as well")
        val marshaller = getMarshaller(typeCodenameOpt.get)
        (Json.toJson(typeCodenameOpt.get), marshaller.marshal(value))
      })
      .getOrElse((JsString("null"), JsNull))

    val varPath = valueLogic.pathNameOpt.get
    (JsObject(Map("var" -> JsString(varPath), "type" -> jsType)), JsObject(Map(varPath -> jsonLogicDatum)))
  }

  /**
   * Returns tuple of serialized (logic, data) of scala data structure [[com.celadari.jsonlogicscala.tree.ComposeLogic]].
   * Json-logic-typed format requires logic and data to split.
   * @param composeLogic: input ComposeLogic to be split into (logic JsValue, data JsValue).
   * @return (logic JsValue, data JsValue)
   */
  protected[this] def serializeComposeLogic(composeLogic: ComposeLogic): (JsValue, JsObject) = {
    // retrieve compose logic attributes
    val operator = composeLogic.operator
    val conditions = composeLogic.conditions

    // create js map operator -> conditions
    val (jsonLogic, jsonLogicData) = serializeArrayOfConditions(conditions)
    (JsObject(Map(operator -> jsonLogic)), jsonLogicData)
  }

  /**
   * Returns tuple of serialized (logic, data) of scala data structure [[Array[com.celadari.jsonlogicscala.tree.JsonLogicCore]]].
   * Logic is returned in an array of JsValue and data by merging data JsObject of each condition.
   * @param conditions: array of [[com.celadari.jsonlogicscala.tree.JsonLogicCore]].
   * @return tuple of split (logic JsValue, data JsObject) from conditions.
   */
  protected[this] def serializeArrayOfConditions(conditions: Array[JsonLogicCore]): (JsValue, JsObject) = {
    val (jsonLogics, jsonLogicData) = conditions.map(jsonLogic => serialize(jsonLogic)).unzip
    (JsArray(jsonLogics), jsonLogicData.map(_.as[JsObject]).reduce(_ ++ _))
  }

  /**
   * Returns tuple of serialized (logic, data) of scala data structure [[com.celadari.jsonlogicscala.tree.JsonLogicCore]].
   * Json-logic-typed format requires logic and data to split.
   * @param jsonLogic: input JsonLogicCore to be split into (logic JsValue, data JsValue).
   * @return (logic JsValue, data JsValue)
   */
  def serialize(jsonLogic: JsonLogicCore): (JsValue, JsValue) = {
    jsonLogic match {
      case valueLogic: ValueLogic[_] => serializeValueLogic(valueLogic)
      case composeLogic: ComposeLogic => serializeComposeLogic(composeLogic)
      case _: VariableLogic => {
        throw new InvalidValueLogicException("VariableLogic cannot be serialized. CompositionOperator.ComposeJsonLogicCore output is only used at evaluation.")
      }
    }
  }
}
