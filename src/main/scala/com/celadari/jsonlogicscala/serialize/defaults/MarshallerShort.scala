// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala short value to json format number value.
 */
object MarshallerShort extends Marshaller {

  /**
   * Converts a scala short value to a json number value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case short: Short => JsNumber(short)
      case short: java.lang.Short => JsNumber(short.toShort)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerShort: $other.\nMarshallerShort can only be applied to short values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
    JsNumber(value.toString.toShort)
  }
}
