// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsString, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format string value to scala string value.
 */
object UnmarshallerString extends Unmarshaller{

  /**
   * Converts json string value to scala string value.
   * @param jsValue: json to deserialized to String value.
   * @return scala string value.
   */
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsString(string) => string
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerString: $other.\nUnmarshallerString could not unmarshall to " +
          "String value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
