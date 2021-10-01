// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format byte value to scala byte value.
 */
object UnmarshallerByte extends Unmarshaller{

  /**
   * Converts json byte value to scala byte value.
   * @param jsValue: json to deserialized to Byte value.
   * @return scala byte value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toByte
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerByte: $other.\nUnmarshallerByte could not unmarshall to Byte value." +
          "\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
