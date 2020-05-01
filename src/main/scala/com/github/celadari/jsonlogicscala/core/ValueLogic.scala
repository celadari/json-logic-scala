package com.github.celadari.jsonlogicscala.core

import java.util.UUID.randomUUID
import play.api.libs.json._

object ValueLogic {

  /**
   * Returns an empty condition.
   * @since 1.1.0
   * @return [[ValueLogic]] instance.
   */
  def empty[T]: ValueLogic[T] = new ValueLogic[T]("", None, randomUUID.toString)

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
                          valueOpt: Option[T],
                          codename: String = randomUUID.toString
                        ) extends JsonLogicCore(operator) {

  /**
   * Indicates if this represents an empty condition.
   * @since 1.1.0
   * @return boolean to indicate if [[ValueLogic.valueOpt]] is [[None]].
   */
  def isEmpty: Boolean = valueOpt.isEmpty
}