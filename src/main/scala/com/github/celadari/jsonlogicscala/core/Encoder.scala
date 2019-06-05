package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._


object Encoder {
  implicit val defaultEncoder: Encoder = new Encoder {
    override def customEncode(valueLogic: ValueLogic[_], otherType: String)(implicit writes: Array[Writes[_]]): JsValue =
      throw new NotImplementedError("You must provide a decoding method for this object.")
  }
}

abstract class Encoder {
  def customEncode(json: ValueLogic[_], otherType: String)(implicit writes: Array[Writes[_]]): JsValue

  def encode(valueLogic: ValueLogic[_])(implicit writes: Array[Writes[_]] = Array()): (String, JsValue) = {
    if (valueLogic.valueOpt.isEmpty) return ("", JsObject.empty)


    val codetype = valueLogic.codetype
    val value = valueLogic.valueOpt.get
    val jsValue = codetype match {
      case "string" => JsString(value.asInstanceOf[String])
      case "byte" => JsNumber(value.asInstanceOf[Short].toInt)
      case "short" => JsNumber(value.asInstanceOf[Short].toInt)
      case "int" => JsNumber(value.asInstanceOf[Int])
      case "long" => JsNumber(value.asInstanceOf[Long])
      case "float" => JsNumber(value.asInstanceOf[Float].toDouble)
      case "double" => JsNumber(value.asInstanceOf[Double])
      case "boolean" => JsBoolean(value.asInstanceOf[Boolean])
      case otherType => customEncode(valueLogic, otherType)(writes)
    }
    (codetype, jsValue)
  }
}