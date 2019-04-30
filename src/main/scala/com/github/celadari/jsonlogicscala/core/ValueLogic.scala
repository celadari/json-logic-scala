package com.github.celadari.jsonlogicscala.core


import play.api.libs.json._

object ValueLogic {

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): ValueLogic[_] = {
    decoder.decode(jsonLogic, jsonLogicData)
  }

}

case class ValueLogic[T](override val operator: String, valueOpt: Option[T]) extends JsonLogicCore(operator) {

  def isEmpty: Boolean = valueOpt.isEmpty
}