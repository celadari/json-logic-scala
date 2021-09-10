package com.celadari.jsonlogicscala.tree

import play.api.libs.json.{JsResult, JsValue, Reads, Writes}
import com.celadari.jsonlogicscala.deserialize.Deserializer
import com.celadari.jsonlogicscala.serialize.Serializer


object ComposeLogic {

  implicit def composeLogicReads(implicit deserializer: Deserializer): Reads[ComposeLogic] = new Reads[ComposeLogic] {
    override def reads(json: JsValue): JsResult[ComposeLogic] = {
      JsonLogicCore.jsonLogicCoreReads.reads(json).map(_.asInstanceOf[ComposeLogic])
    }
  }

  implicit def composeLogicWrites(implicit serializer: Serializer): Writes[ComposeLogic] = new Writes[ComposeLogic] {
    override def writes(composeLogic: ComposeLogic): JsValue = {
      JsonLogicCore.jsonLogicCoreWrites(serializer).writes(composeLogic)
    }
  }
  def unapply(composeLogic: ComposeLogic): Some[(String, Array[JsonLogicCore])] = Some((composeLogic.operator, composeLogic.conditions))
}

class ComposeLogic(override val operator: String, private[this] var _conditions: Array[JsonLogicCore]) extends JsonLogicCore(operator) {

  def conditions: Array[JsonLogicCore] = _conditions
  def conditions_=(conds: Array[JsonLogicCore]): Unit = _conditions = conds

}
