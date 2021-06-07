package com.github.celadari.jsonlogicscala.tree

import java.util.UUID
import play.api.libs.json.{JsResult, JsValue, Reads}
import com.github.celadari.jsonlogicscala.tree.types.TypeValue
import com.github.celadari.jsonlogicscala.deserialize.Deserializer
import com.github.celadari.jsonlogicscala.exceptions.TreeException


object ValueLogic {
  val OPERATOR_CODENAME: String = "var"

  implicit def valueLogicReads(implicit deserializer: Deserializer): Reads[ValueLogic[_]] = new Reads[ValueLogic[_]] {
    override def reads(json: JsValue): JsResult[ValueLogic[_]] = {
      JsonLogicCore.jsonLogicCoreReads.reads(json).map(_.asInstanceOf[ValueLogic[_]])
    }
  }
}

case class ValueLogic[T](
                          valueOpt: Option[T],
                          typeCodenameOpt: Option[TypeValue] = None,
                          variableNameOpt: Option[String] = None,
                          pathNameOpt: Option[String] = Some(UUID.randomUUID().toString)
                        ) extends JsonLogicCore(ValueLogic.OPERATOR_CODENAME) {

  if ((variableNameOpt.isDefined && pathNameOpt.isDefined) || (variableNameOpt.isEmpty && pathNameOpt.isEmpty)) {
    throw new TreeException(s"ValueLogic cannot be both variable compose and data: variableNameOpt and pathNameOpt cannot be both defined or empty\n$this")
  }
}
