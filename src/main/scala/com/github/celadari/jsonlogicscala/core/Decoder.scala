package com.github.celadari.jsonlogicscala.core

import play.api.libs.json.{JsValue, Reads}

object Decoder {
  implicit val defaultDecoder: Decoder = new Decoder {
    override def customDecode(json: JsValue, otherType: String)(implicit reads: Array[Reads[_]] = Array()): Any =
      throw new NotImplementedError("You must provide a decoding method for this object.")
  }
}

abstract class Decoder {
  def customDecode(json: JsValue, otherType: String)(implicit reads: Array[Reads[_]]): Any

  def decode(jsValue: JsValue, `type`: String)(implicit reads: Array[Reads[_]] = Array()): ValueLogic[_] = {
    val value = `type` match {
      case "byte" => jsValue.as[Byte]
      case "short" => jsValue.as[Short]
      case "int" => jsValue.as[Int]
      case "long" => jsValue.as[Long]
      case "string" => jsValue.as[String]
      case "float" => jsValue.as[Float]
      case "double" => jsValue.as[Double]
      case "boolean" => jsValue.as[Boolean]
      case otherType => customDecode(jsValue, otherType)(reads)
    }
    ValueLogic("var", value)
  }
}
