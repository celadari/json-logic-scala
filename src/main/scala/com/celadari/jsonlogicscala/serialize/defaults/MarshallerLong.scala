// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala long value to json format number value.
 */
object MarshallerLong extends Marshaller {

  /**
   * Converts a scala long value to a json number value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case longValue: Long => JsNumber(longValue)
      case longValue: java.lang.Long => JsNumber(longValue.toLong)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerLong: $other.\nMarshallerLong can only be applied to Long values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
