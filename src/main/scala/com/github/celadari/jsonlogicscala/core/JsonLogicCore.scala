package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object JsonLogicCore {

  implicit def jsonLogicCoreReads[T](implicit fmt: Reads[T]): Reads[JsonLogicCore[T]] = new Reads[JsonLogicCore[T]] {

    def readArrayOfConditions(json: JsValue): Array[JsonLogicCore[_]] = {
      val jsArray = json.asInstanceOf[JsArray]
      jsArray
        .value
        .map(jsValue => {
          reads(jsValue).get
        })
        .toArray
    }

    override def reads(json: JsValue): JsResult[JsonLogicCore[T]] = {
        // JsValue is supposed to be JsArray
        val jsObject = json.asInstanceOf[JsObject]

        // check for operator field
        val fields = jsObject.fields

        // if operator is data access
        if (fields.map(_._1).contains("var")) return JsSuccess(ValueLogic.valueLogicReads[T](fmt).reads(jsObject).get)

        // check for compose logic operator field
        if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
        val operator = fields.head._1
        if (!ComposeLogic.OPERATORS.contains(operator)) throw new Error(s"Invalid parsed operator: $operator")

        // if operator is compose logic
        JsSuccess(ComposeLogic.composeLogicReads[T].reads(jsObject).get)
    }
  }
}


abstract class JsonLogicCore[T](operator: String)