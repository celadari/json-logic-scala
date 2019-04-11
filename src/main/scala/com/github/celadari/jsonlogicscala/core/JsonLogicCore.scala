package com.github.celadari.jsonlogicscala.core

import com.github.celadari.jsonlogicscala.operators.ReduceLogic
import play.api.libs.json._

object JsonLogicCore {

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): JsonLogicCore = {
    // check for operator field
    val fields = jsonLogic.fields

    // if operator is data access
    if (fields.map(_._1).contains("var")) return ValueLogic.decode(jsonLogic, jsonLogicData)(decoder)

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
    val operator = fields.head._1
    if (!ComposeLogic.OPERATORS.contains(operator)) throw new Error(s"Invalid parsed operator: $operator")

    // if operator is compose logic
    ComposeLogic.decode(jsonLogic, jsonLogicData)(decoder)
  }

  implicit def jsonLogicCoreReads(implicit decoder: Decoder): Reads[JsonLogicCore] = new Reads[JsonLogicCore] {

    override def reads(json: JsValue): JsResult[JsonLogicCore] = {

      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = (json \ 1).getOrElse(JsObject.empty).asInstanceOf[JsObject]

      // apply reading with distinguished components: logic and data
      JsSuccess(decode(jsonLogic, jsonLogicData)(decoder))
    }
  }
}


abstract class JsonLogicCore(val operator: String) {

  def reduce(implicit reducer: ReduceLogic): Any = {
    reducer.reduce(this)
  }
}