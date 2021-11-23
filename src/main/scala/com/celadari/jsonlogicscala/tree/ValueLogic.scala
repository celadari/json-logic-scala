// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree

import java.util.UUID
import play.api.libs.json.{JsResult, JsValue, Reads}
import com.celadari.jsonlogicscala.deserialize.Deserializer
import com.celadari.jsonlogicscala.exceptions.TreeException
import com.celadari.jsonlogicscala.tree.types.TypeValue


/**
 * Companion object that holds implicit reader json.
 */
object ValueLogic {
  val OPERATOR_CODENAME: String = "var"

  implicit def valueLogicReads(implicit deserializer: Deserializer): Reads[ValueLogic[_]] = new Reads[ValueLogic[_]] {
    override def reads(json: JsValue): JsResult[ValueLogic[_]] = {
      JsonLogicCore.jsonLogicCoreReads.reads(json).map(_.asInstanceOf[ValueLogic[_]])
    }
  }
}


/**
 * Represents a leaf node in syntax tree. It is a data node.
 * Scala data structures representing expressions from json-logic-typed format are based on abstract syntax tree.
 * @param valueOpt: optional data value represented by this node.
 * @param typeOpt: optional [[com.celadari.jsonlogicscala.tree.types.TypeValue]]. None value only under composed operators (advanced use).
 * @param variableNameOpt: optional string of variableNameOpt. Only defined under composed operators.
 * @param pathNameOpt: optional string to represent name of data in json-format-type. Also used as variable name under composed operators.
 * @note inherited operator has fixed value of "var".
 */
case class ValueLogic[T](
                          valueOpt: Option[T],
                          typeOpt: Option[TypeValue] = None,
                          variableNameOpt: Option[String] = None,
                          pathNameOpt: Option[String] = Some(UUID.randomUUID().toString)
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME) {

  if ((variableNameOpt.isDefined && pathNameOpt.isDefined) || (variableNameOpt.isEmpty && pathNameOpt.isEmpty)) {
    throw new TreeException(s"ValueLogic cannot be both variable compose and data: variableNameOpt and pathNameOpt cannot be both defined or empty\n$this")
  }
}
