package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object ComposeLogic {

  val OPERATORS = Array("<=", "<", ">=", ">", "==", "!=", "or", "and", "xor")

  implicit def composeLogicReads[T](implicit fmt: Reads[T]): Reads[ComposeLogic[T]] = new Reads[ComposeLogic[T]] {

    override def reads(json: JsValue): JsResult[ComposeLogic[T]] = {
      // check for operator field
      val fields = json.asInstanceOf[JsObject].fields

      // check for compose logic operator field
      if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
      val operator = fields.head._1
      if (!ComposeLogic.OPERATORS.contains(operator)) throw new Error(s"Invalid parsed operator: $operator")

      // if operator is compose logic
      JsSuccess(ComposeLogic[T](operator, readArrayOfConditions((json \ operator).get)))
    }

    def readArrayOfConditions(json: JsValue): Array[JsonLogicCore[_]] = {
      val jsArray = json.asInstanceOf[JsArray]
      jsArray
        .value
        .map(jsValue => {
          JsonLogicCore.jsonLogicCoreReads[T].reads(jsValue).get
        })
        .toArray
    }

  }
}

case class ComposeLogic[T](operator: String, conditions: Array[JsonLogicCore[_]]) extends JsonLogicCore[T](operator)