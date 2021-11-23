// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNull, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala null value to json format null value.
 */
object MarshallerNull extends Marshaller {

  /**
   * Converts a scala null value to a json null value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    if (value == null) JsNull
    else {
      throw new IllegalInputException(s"Illegal input argument to MarshallerNull: $value.\nMarshallerNull can only be applied to null value." +
        "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
    }
  }
}
