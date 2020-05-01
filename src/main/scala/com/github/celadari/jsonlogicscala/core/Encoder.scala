package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object Encoder {
  implicit val defaultEncoder: Encoder = new Encoder {
    override def customValueAndType(value: Any): (String, JsValue) =
      throw new NotImplementedError("You must provide a decoding method for this object.")
  }
}

abstract class Encoder {
  def customValueAndType(value: Any): (String, JsValue)

  def getJsValueAndType(value: Any): (String, JsValue) = {
    if (Option(value).isEmpty) return ("null", JsNull)

    value match {
      case value: String => ("string", JsString(value))
      case value: Byte => ("byte", JsNumber(value.toInt))
      case value: Short => ("short", JsNumber(value.toInt))
      case value: Int => ("int", JsNumber(value))
      case value: Long => ("long", JsNumber(value))
      case value: Float => ("float", JsNumber(value.toDouble))
      case value: Double => ("double", JsNumber(value))
      case value: Boolean => ("boolean", JsBoolean(value))
      case arr: Array[_] => {
        if (arr.isEmpty) ("array[]", JsArray.empty)
        else {
          val (typesArr, jsArrs) = arr.map(getJsValueAndType).unzip
          (s"array[${typesArr.head}]", JsArray(jsArrs))
        }
      }
      case otherType => customValueAndType(otherType)
    }
  }

  def encode(valueLogic: ValueLogic[_]): (String, String, JsValue) = {
    val codenameData = valueLogic.codename

    val (typeData, jsValue) = getJsValueAndType(valueLogic.valueOpt.orNull)
    (typeData, codenameData, jsValue)
  }
}