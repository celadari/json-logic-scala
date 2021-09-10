package com.celadari.jsonlogicscala.tree

import play.api.libs.json._
import com.celadari.jsonlogicscala.deserialize.Deserializer
import com.celadari.jsonlogicscala.serialize.Serializer


object JsonLogicCore {

  implicit def jsonLogicCoreReads(implicit deserializer: Deserializer): Reads[JsonLogicCore] = new Reads[JsonLogicCore] {

    override def reads(json: JsValue): JsResult[JsonLogicCore] = {

      // split json in two components jsonLogic and jsonLogicData
      val jsonLogic = (json \ 0).getOrElse(JsObject.empty).asInstanceOf[JsObject]
      val jsonLogicData = (json \ 1).getOrElse(JsObject.empty).asInstanceOf[JsObject]

      // apply reading with distinguished components: logic and data
      JsSuccess(deserializer.deserialize(jsonLogic, jsonLogicData))
    }
  }

  implicit def jsonLogicCoreWrites(implicit serializer: Serializer): Writes[JsonLogicCore] = new Writes[JsonLogicCore] {

    override def writes(jsonLogicCore: JsonLogicCore): JsValue = {
      // apply writing
      val (jsonLogic, jsonLogicData) = serializer.serialize(jsonLogicCore)

      // return final result
      JsArray(Array(jsonLogic, jsonLogicData))
    }
  }

  def traverseRoot(jsonLogicCore: JsonLogicCore, errorConditionOpt: Option[JsonLogicCore]): String = {
    val errorString = errorConditionOpt.filter(_ == jsonLogicCore).map(_ => "ERROR AT: ").getOrElse("")

    jsonLogicCore match {
      case valueLogic: ValueLogic[_] => {
        if (valueLogic.pathNameOpt.isDefined) errorString ++ s"{ValueLogic Data '${valueLogic.pathNameOpt.get}': ${valueLogic.valueOpt.orNull}}"
        else errorString ++ s"{ValueLogic Variable '${valueLogic.variableNameOpt.get}'}"
      }
      case composeLogic: ComposeLogic => {
        val sb = new StringBuilder
        sb.append(errorString)
        sb.append(jsonLogicCore.operator)

        val isTailNonEmpty = composeLogic.conditions.tail.nonEmpty
        val pointerForHead = if (isTailNonEmpty) "├──" else "└──"
        val nbConds = composeLogic.conditions.length

        composeLogic.conditions.headOption.foreach(nodeHeader => traverseNodes(sb, "", pointerForHead, nodeHeader, errorConditionOpt))
        composeLogic.conditions.slice(1, nbConds - 1).foreach(node => traverseNodes(sb, "", "├──", node, errorConditionOpt))
        composeLogic.conditions.slice(math.max(1, nbConds - 1), nbConds).foreach(nodeLast => {
          traverseNodes(sb, "", "└──", nodeLast, errorConditionOpt)
        })

        sb.toString
      }
    }
  }

  def traverseNodes(
                     sb: StringBuilder,
                     padding: String,
                     pointer: String,
                     jsonLogicCore: JsonLogicCore,
                     errorConditionOpt: Option[JsonLogicCore]
                   ): Unit = {
    sb ++= "\n"
    sb ++= padding
    sb ++= pointer

    errorConditionOpt.filter(_ == jsonLogicCore).foreach(_ => sb ++= "ERROR AT: ")

    jsonLogicCore match {
      case valueLogic: ValueLogic[_] => {
        if (valueLogic.pathNameOpt.isDefined) sb ++= s"{ValueLogic Data '${valueLogic.pathNameOpt.get}': ${valueLogic.valueOpt.orNull}}"
        else sb ++= s"{ValueLogic Variable '${valueLogic.variableNameOpt.get}'}"
      }
      case variableLogic: VariableLogic => {
        sb ++= s"{VariableLogic '${variableLogic.variableName}' of '${variableLogic.composeOperator}' compose operator}"
      }
      case composeLogic: ComposeLogic => {
        sb ++= composeLogic.operator

        val paddingBuilder = new StringBuilder(padding)
        paddingBuilder ++= "│  "

        val paddingForAll = paddingBuilder.toString
        val isTailNonEmpty = composeLogic.conditions.tail.nonEmpty
        val pointerForHead = if (isTailNonEmpty) "├──" else "└──"
        val nbConds = composeLogic.conditions.length

        composeLogic.conditions.headOption.foreach(nodeHeader => traverseNodes(sb, paddingForAll, pointerForHead, nodeHeader, errorConditionOpt))
        composeLogic.conditions.slice(1, nbConds - 1).foreach(node => traverseNodes(sb, paddingForAll, "├──", node, errorConditionOpt))
        composeLogic.conditions.slice(math.max(1, nbConds - 1), nbConds).foreach(nodeTail => {
          traverseNodes(sb, paddingForAll, "└──", nodeTail, errorConditionOpt)
        })
      }
    }
  }

}


abstract class JsonLogicCore(val operator: String) {

  def treeString: String = {
    JsonLogicCore.traverseRoot(this, None)
  }
}
