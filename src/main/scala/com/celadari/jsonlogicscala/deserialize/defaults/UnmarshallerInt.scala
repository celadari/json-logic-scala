// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format int value to scala int value.
 */
object UnmarshallerInt extends Unmarshaller{

  /**
   * Converts json int value to scala int value.
   * @param jsValue: json to deserialized to Int value.
   * @return scala int value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toInt
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerInt: $other.\nUnmarshallerInt could not unmarshall to Int value." +
          "\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
