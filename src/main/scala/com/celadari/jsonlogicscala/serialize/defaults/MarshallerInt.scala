// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala int value to json format number value.
 */
object MarshallerInt extends Marshaller {

  /**
   * Converts a scala int value to a json number value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case intValue: Int => JsNumber(intValue)
      case intValue: java.lang.Integer => JsNumber(intValue.toInt)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerInt: $other.\nMarshallerInt can only be applied to Int values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
