// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsBoolean, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala boolean value to json format boolean value.
 */
object MarshallerBoolean extends Marshaller {

  /**
   * Converts a scala boolean value to a json boolean value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case boolean: Boolean => JsBoolean(boolean)
      case bool: java.lang.Boolean => JsBoolean(bool)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerBoolean: $other.\nMarshallerBoolean can only be applied to boolean values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
