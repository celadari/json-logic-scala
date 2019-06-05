package com.github.celadari.jsonlogicscala.core

import math.random
import play.api.libs.json._

object ValueLogic {

  private[core] def encode(valueLogic: ValueLogic[_])(implicit encoder: Encoder): (JsValue, JsObject) = {
    val codename = random.toString
    val (typeData, jsonData) = encoder.encode(valueLogic)

    val jsonLogic = JsObject(Map("var" -> JsString(codename), "type" -> JsString(typeData)))
    val jsonLogicData = JsObject(Map(codename -> jsonData))
    (jsonLogic, jsonLogicData)
  }

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): ValueLogic[_] = {
    decoder.decode(jsonLogic, jsonLogicData)
  }

}

case class ValueLogic[T](override val operator: String, valueOpt: Option[T]) extends JsonLogicCore(operator) {

  def isEmpty: Boolean = valueOpt.isEmpty
}