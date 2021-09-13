// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala double value to json format number value.
 */
object MarshallerDouble extends Marshaller {

  /**
   * Converts a scala double value to a json double value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case doubleValue: Double => JsNumber(doubleValue)
      case doubleValue: java.lang.Double => JsNumber(doubleValue.toDouble)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerDouble: $other.\nMarshallerDouble can only be applied to Double values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
