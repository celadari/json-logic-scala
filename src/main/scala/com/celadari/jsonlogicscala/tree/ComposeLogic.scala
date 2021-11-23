// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree

import play.api.libs.json.{JsResult, JsValue, Reads, Writes}
import com.celadari.jsonlogicscala.deserialize.Deserializer
import com.celadari.jsonlogicscala.serialize.Serializer


/**
 * Companion object that holds implicit reader and writer json.
 */
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

/**
 * Represents an internal node in syntax tree. It is an operator node.
 * Scala data structures representing expressions from json-logic-typed format are based on abstract syntax tree.
 * @param operator: string codename.
 * @param _conditions: children [[JsonLogicCore]] nodes. They are the operands the operator must be applied to.
 */
class ComposeLogic(override val operator: String, private[this] var _conditions: Array[JsonLogicCore]) extends JsonLogicCore(operator) {

  /**
   * Returns array of children nodes.
   * @return _conditions.
   */
  def conditions: Array[JsonLogicCore] = _conditions

  /**
   * Sets children nodes.
   * Useful with composed operators.
   * @param conds: new set of children nodes array.
   */
  def conditions_=(conds: Array[JsonLogicCore]): Unit = _conditions = conds

}
