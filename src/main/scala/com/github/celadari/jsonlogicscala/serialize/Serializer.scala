package com.github.celadari.jsonlogicscala.serialize

import play.api.libs.json._
import com.github.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}
import com.github.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, OptionTypeValue, SimpleTypeValue, TypeValue}
import com.github.celadari.jsonlogicscala.exceptions.{IllegalInputException, InvalidValueLogicException}
import com.github.celadari.jsonlogicscala.converters.CollectionConverters.HasMapValues


object Serializer {
  implicit val defaultSerializer: Serializer = new Serializer()
}

class Serializer(implicit val conf: SerializerConf) {
  protected[this] val marshallers: Map[String, Marshaller] = if (conf.isPriorityToManualAdd) conf.marshallerMetaInfAdd ++ conf.marshallersManualAdd
    else conf.marshallersManualAdd ++ conf.marshallerMetaInfAdd

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

  protected[this] def serializeComposeLogic(composeLogic: ComposeLogic): (JsValue, JsObject) = {
    // retrieve compose logic attributes
    val operator = composeLogic.operator
    val conditions = composeLogic.conditions

    // create js map operator -> conditions
    val (jsonLogic, jsonLogicData) = serializeArrayOfConditions(conditions)
    (JsObject(Map(operator -> jsonLogic)), jsonLogicData)
  }

  protected[this] def serializeArrayOfConditions(conditions: Array[JsonLogicCore]): (JsValue, JsObject) = {
    val (jsonLogics, jsonLogicData) = conditions.map(jsonLogic => serialize(jsonLogic)).unzip
    (JsArray(jsonLogics), jsonLogicData.map(_.as[JsObject]).reduce(_ ++ _))
  }

  def serialize(jsonLogic: JsonLogicCore): (JsValue, JsValue) = {
    // if operator is data access
    jsonLogic match {
      case valueLogic: ValueLogic[_] => serializeValueLogic(valueLogic)
      case composeLogic: ComposeLogic => serializeComposeLogic(composeLogic)
      case _: VariableLogic => {
        throw new InvalidValueLogicException("VariableLogic cannot be serialized. CompositionOperator.ComposeJsonLogicCore output is only used at evaluation.")
      }
    }
  }
}
