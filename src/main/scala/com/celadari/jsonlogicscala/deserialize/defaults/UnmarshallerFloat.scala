// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format float value to scala float value.
 */
object UnmarshallerFloat extends Unmarshaller{

  /**
   * Converts json float value to scala float value.
   * @param jsValue: json to deserialized to Float value.
   * @return scala float value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toFloat
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerFloat: $other.\nUnmarshallerFloat could not unmarshall " +
          "to Float value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
