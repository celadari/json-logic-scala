package com.celadari.jsonlogicscala.tree.types

import play.api.libs.json.{JsError, JsObject, JsResult, JsString, JsSuccess, JsValue, Reads, Writes}
import com.celadari.jsonlogicscala.exceptions.{IllegalInputException, InvalidJsonParsingException}


object TypeValue {

  private[this] def parseTypeValue(json: JsValue): TypeValue = {
    val codename = (json \ "codename").as[String]
    val paramType = (json \ "paramType").asOpt[JsValue]

    if (!json.as[JsObject].keys.forall(key => key == "codename" || key == "paramType")) {
      throw new InvalidJsonParsingException(s"TypeValue cannot have other keys than 'codename' and 'paramType': $json")
    }

    paramType
      .map(jsParamType => codename match {
        case ArrayTypeValue.CODENAME_TYPE => ArrayTypeValue(parseTypeValue(jsParamType))
        case MapTypeValue.CODENAME_TYPE => MapTypeValue(parseTypeValue(jsParamType))
        case OptionTypeValue.CODENAME_TYPE => OptionTypeValue(parseTypeValue(jsParamType))
        case other => throw new InvalidJsonParsingException(s"Illegal 'codename': '$other' in parameter type. SimpleTypeValue cannot have 'paramType' key")
      })
      .getOrElse(SimpleTypeValue(codename))
  }

  implicit val readTypeValue: Reads[TypeValue] = new Reads[TypeValue] {
    override def reads(json: JsValue): JsResult[TypeValue] = {
      try {
        JsSuccess(parseTypeValue(json))
      }
      catch {
        case _: Throwable => JsError("Wrong json format for type value")
      }
    }
  }

  private[this] def serializeTypeValue(typeValue: TypeValue): JsValue = {
    typeValue match {
      case SimpleTypeValue(codename) => JsObject(Map("codename" -> JsString(codename)))
      case ArrayTypeValue(paramType) => JsObject(Map(
        "codename" -> JsString(ArrayTypeValue.CODENAME_TYPE),
        "paramType" -> serializeTypeValue(paramType)
      ))
      case MapTypeValue(paramType) => JsObject(Map(
        "codename" -> JsString(MapTypeValue.CODENAME_TYPE),
        "paramType" -> serializeTypeValue(paramType)
      ))
      case OptionTypeValue(paramType) => JsObject(Map(
        "codename" -> JsString(OptionTypeValue.CODENAME_TYPE),
        "paramType" -> serializeTypeValue(paramType)
      ))
      case other => throw new IllegalInputException(s"'$other' type cannot be serialized")
    }
  }

  implicit val writesTypeValue: Writes[TypeValue] = new Writes[TypeValue] {
    override def writes(o: TypeValue): JsValue = serializeTypeValue(o)
  }

}

abstract class TypeValue(val codename: String)

