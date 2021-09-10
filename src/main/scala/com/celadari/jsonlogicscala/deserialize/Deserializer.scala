package com.celadari.jsonlogicscala.deserialize

import play.api.libs.json.{JsArray, JsLookupResult, JsNull, JsObject, JsValue}
import com.celadari.jsonlogicscala.converters.CollectionConverters.HasMapValues
import com.celadari.jsonlogicscala.exceptions.{IllegalInputException, InvalidJsonParsingException}
import com.celadari.jsonlogicscala.tree.types.{AnyTypeValue, ArrayTypeValue, MapTypeValue, OptionTypeValue, SimpleTypeValue, TypeValue}
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic}


object Deserializer {

  def unmarshType(jsLookupResult: JsLookupResult): Option[TypeValue] = {
    if (jsLookupResult.isDefined) {
      try {
        Some(jsLookupResult.get.as[TypeValue])
      }
      catch {
        case _: Throwable => throw new InvalidJsonParsingException(s"Invalid typevalue json format: ${jsLookupResult.get.toString}")
      }
    }
    else None
  }
  implicit val defaultDeserializer: Deserializer = new Deserializer()
}

class Deserializer(implicit val conf: DeserializerConf) {
  protected[this] val unmarshallers: Map[String, Unmarshaller] = if (conf.isPriorityToManualAdd) conf.unmarshallerMetaInfAdd ++ conf.unmarshallersManualAdd
                                                                 else conf.unmarshallersManualAdd ++ conf.unmarshallerMetaInfAdd

  protected[this] def getUnmarshaller(typeValue: TypeValue): Unmarshaller = {
    typeValue match {
      case SimpleTypeValue(codename) => unmarshallers.getOrElse(codename, throw new IllegalInputException(s"No unmarshaller defined for $codename"))
      case ArrayTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = jsValue.as[JsArray].value.toArray.map(jsValue => getUnmarshaller(paramType).unmarshal(jsValue))
      }
      case MapTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = {
          jsValue.as[JsObject].value.mapValuesAccordingToScalaVersion(jsValue => getUnmarshaller(paramType).unmarshal(jsValue))
        }
      }
      case OptionTypeValue(paramType) => new Unmarshaller {
        override def unmarshal(jsValue: JsValue): Any = {
          if (jsValue == JsNull) None
          else Some(getUnmarshaller(paramType).unmarshal(jsValue))
        }
      }
      case AnyTypeValue => {
        throw new IllegalArgumentException("Cannot serialize type AnyTypeValue.\nAnyTypeValue is for sole use at evaluation for composition operators")
      }
    }
  }

  protected[this] def deserializeValueLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ValueLogic[_] = {
    val isTypeDefined = (jsonLogic \ "type").isDefined
    val typeValueOpt = Deserializer.unmarshType(jsonLogic \ "type")
    val pathData = (jsonLogic \ "var").as[String]
    val lookUpPathData = jsonLogicData \ pathData
    val jsValue = lookUpPathData.getOrElse(JsNull)

    if (isTypeDefined && lookUpPathData.isEmpty) {
      throw new InvalidJsonParsingException(s"""Error while parsing ValueLogic of type value: "var" $pathData is undefined""")
    }
    if (!isTypeDefined && lookUpPathData.isDefined) {
      throw new InvalidJsonParsingException(
        """Error while parsing ValueLogic of type variable: "var" must not be a key on data dictionary.""" +
        s"""\nActual: "$pathData"""")
    }

    val valueOpt = typeValueOpt.flatMap(typeValue => Option(getUnmarshaller(typeValue).unmarshal(jsValue)))
    val variableNameOpt = if (lookUpPathData.isDefined) None else Some(pathData)
    val pathDataOpt = if (lookUpPathData.isDefined) Some(pathData) else None

    ValueLogic(valueOpt, typeValueOpt, variableNameOpt, pathDataOpt)
  }

  protected[this] def deserializeComposeLogic(jsonLogic: JsObject, jsonLogicData: JsObject): ComposeLogic = {
    // check for operator field
    val fields = jsonLogic.fields

    // check for compose logic operator field
    if (fields.length > 1) {
      throw new InvalidJsonParsingException("ComposeLogic cannot have more than one operator field.\nCurrent operators: " +
        fields.map(_._1).mkString("[", ", ", "]") +
        s"\nInvalid ComposeLogic json: ${jsonLogic.toString}")
    }
    if (fields.isEmpty) throw new InvalidJsonParsingException("ComposeLogic cannot be empty")
    val operator = fields.head._1

    // if operator is compose logic
    new ComposeLogic(operator, deserializeArrayOfConditions((jsonLogic \ operator).get, jsonLogicData))
  }

  protected[this] def deserializeArrayOfConditions(json: JsValue, jsonLogicData: JsObject): Array[JsonLogicCore] = {
    val jsArray = json.asInstanceOf[JsArray]
    jsArray
      .value
      .map(jsValue => {
        deserialize(jsValue.asInstanceOf[JsObject], jsonLogicData)
      })
      .toArray
  }

  // scalastyle:off return
  def deserialize(jsonLogic: JsObject, jsonLogicData: JsObject): JsonLogicCore = {
    // check for operator field
    val fields = jsonLogic.fields

    // if operator is data access
    if (fields.map(_._1).contains("var")) return deserializeValueLogic(jsonLogic, jsonLogicData)

    // if operator is compose logic
    deserializeComposeLogic(jsonLogic, jsonLogicData)
  }

}
