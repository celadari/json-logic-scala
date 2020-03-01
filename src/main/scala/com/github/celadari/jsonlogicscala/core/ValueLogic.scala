package com.github.celadari.jsonlogicscala.core

import java.util.UUID.randomUUID
import play.api.libs.json._

object ValueLogic {

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): ValueLogic[_] = {
    decoder.decode(jsonLogic, jsonLogicData)
  }

  private[core] def encode(valueLogic: ValueLogic[_])(implicit encoder: Encoder): (JsValue, JsObject) = {
    // retrieve valueLogic information
    val (typeData, codenameData, jsonData) = encoder.encode(valueLogic)

    // construct jsonLogic component and jsonLogicData component
    val jsonLogic = JsObject(Map("var" -> JsString(codenameData), "type" -> JsString(typeData)))
    val jsonLogicData = JsObject(Map(codenameData -> jsonData))
    (jsonLogic, jsonLogicData)
  }

}

case class ValueLogic[T](
                          override val operator: String,
                          value: T,
                          codename: String = randomUUID.toString
                        ) extends JsonLogicCore(operator)