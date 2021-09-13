// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsString, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala string value to json format string value.
 */
object MarshallerString extends Marshaller {

  /**
   * Converts a scala string value to a json string value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case string: String => JsString(string)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerString: $other.\nMarshallerString can only be applied to string values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
