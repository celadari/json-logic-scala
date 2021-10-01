// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format double value to scala double value.
 */
object UnmarshallerDouble extends Unmarshaller{

  /**
   * Converts json double value to scala double value.
   * @param jsValue: json to deserialized to Double value.
   * @return scala double value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toDouble
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerDouble: $other.\nUnmarshallerDouble could not unmarshall " +
          "to Double value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
