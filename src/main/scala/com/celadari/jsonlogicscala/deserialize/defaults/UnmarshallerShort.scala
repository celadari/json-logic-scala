// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format short value to scala short value.
 */
object UnmarshallerShort extends Unmarshaller{

  /**
   * Converts json short value to scala short value.
   * @param jsValue: json to deserialized to Short value.
   * @return scala short value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toShort
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerShort: $other.\nUnmarshallerShort could not unmarshall to Short " +
          "value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
