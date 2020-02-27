package com.github.celadari.jsonlogicscala.core

import play.api.libs.json._

object ComposeLogic {

  object BINARY_OPERATORS {
    val LTEQ: String = "<="
    val LT: String = "<"
    val GTEQ: String = ">="
    val GT: String = ">"
    val EQ: String = "=="
    val DIFF: String = "!="
    val IN: String = "in"
    val NOT_IN: String = "not in"
    val ALL: Array[String] = Array(LTEQ, LT, GTEQ, GT, EQ, DIFF)
  }

  object MULTIPLE_OPERATORS {
    val OR: String = "or"
    val AND: String = "and"
    val XOR: String = "xor"
    val MAX: String = "max"
    val MIN: String = "min"
    val ALL: Array[String] = Array(OR, AND, XOR, MAX, MIN)
  }
  val OPERATORS: Array[String] = BINARY_OPERATORS.ALL ++ MULTIPLE_OPERATORS.ALL

  private[core] def decode(jsonLogic: JsObject, jsonLogicData: JsObject)(implicit decoder: Decoder): ComposeLogic = {
    // check for operator field
    val fields = jsonLogic.fields

    // check for compose logic operator field
    if (fields.length > 1) throw new Error("JSON object is supposed to have only one operator field.")
    val operator = fields.head._1
    if (!OPERATORS.contains(operator)) throw new Error(s"Invalid parsed operator: $operator")

    // if operator is compose logic
    ComposeLogic(operator, decodeArrayOfConditions((jsonLogic \ operator).get, jsonLogicData)(decoder))
  }


  private[core] def decodeArrayOfConditions(json: JsValue, jsonLogicData: JsObject)(implicit decoder: Decoder): Array[JsonLogicCore] = {
    val jsArray = json.asInstanceOf[JsArray]
    jsArray
      .value
      .map(jsValue => {
        JsonLogicCore.decode(jsValue.asInstanceOf[JsObject], jsonLogicData)(decoder)
      })
      .toArray
  }

}

case class ComposeLogic(override val operator: String, conditions: Array[JsonLogicCore]) extends JsonLogicCore(operator)
