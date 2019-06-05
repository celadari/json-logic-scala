package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._


object Encoder {
  implicit val defaultEncoder: Encoder = new Encoder {
    override def customEncode(valueLogic: ValueLogic[_], otherType: Any)(implicit writes: Array[Writes[_]]): (String, JsValue) =
      throw new NotImplementedError("You must provide a decoding method for this object.")
  }
}

abstract class Encoder {
  def customEncode(json: ValueLogic[_], otherType: Any)(implicit writes: Array[Writes[_]]): (String, JsValue)

  def encode(valueLogic: ValueLogic[_])(implicit writes: Array[Writes[_]] = Array()): (String, JsValue) = {
    if (valueLogic.valueOpt.isEmpty) return ("", JsObject.empty)

    valueLogic.valueOpt.get match {
      case value: String => ("string", JsString(value))
      case value: Byte => ("byte", JsNumber(value.toInt))
      case value: Short => ("short", JsNumber(value.toInt))
      case value: Int => ("int", JsNumber(value))
      case value: Long => ("long", JsNumber(value))
      case value: Float => ("float", JsNumber(value.toDouble))
      case value: Double => ("double", JsNumber(value))
      case value: Boolean => ("boolean", JsBoolean(value))
      case otherType => customEncode(valueLogic, otherType)(writes)
    }
  }
}