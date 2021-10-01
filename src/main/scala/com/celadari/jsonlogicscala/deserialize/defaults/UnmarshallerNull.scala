// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNull, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


/**
 * Default unmarshaller that converts json format null value to scala null value.
 */
object UnmarshallerNull extends Unmarshaller {

  /**
   * Converts json null value to scala null value.
   * @param jsValue: json to deserialized to Null value.
   * @return scala null value.
   */
  // scalastyle:off null
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNull => null
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerNull: $other.\nUnmarshallerNull can only be applied to null value." +
          "\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
