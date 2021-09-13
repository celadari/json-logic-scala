// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala float value to json format number value.
 */
object MarshallerFloat extends Marshaller {

  /**
   * Converts a scala float value to a json number value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case floatValue: Float => JsNumber(floatValue)
      case floatValue: java.lang.Float => JsNumber(floatValue.toFloat)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerFloat: $other.\nMarshallerFloat can only be applied to Float values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
