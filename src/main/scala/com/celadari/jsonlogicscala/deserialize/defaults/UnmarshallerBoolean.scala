// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsBoolean, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format boolean value to scala boolean value.
 */
object UnmarshallerBoolean extends Unmarshaller {

  /**
   * Converts json boolean value to scala boolean value.
   * @param jsValue: json to deserialized to Boolean value.
   * @return scala boolean value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsBoolean(bool) => bool
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerBoolean: $other.\nUnmarshallerBoolean could not unmarshall " +
          "to boolean value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
