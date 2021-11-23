// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format long value to scala long value.
 */
object UnmarshallerLong extends Unmarshaller{

  /**
   * Converts json long value to scala long value.
   * @param jsValue: json to deserialized to Long value.
   * @return scala long value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toLong
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerLong: $other.\nUnmarshallerLong could not unmarshall to Long value." +
          "\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
