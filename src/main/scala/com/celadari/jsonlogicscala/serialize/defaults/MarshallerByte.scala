// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller


/**
 * Default marshaller that converts scala byte value to json format number value.
 */
object MarshallerByte extends Marshaller {

  /**
   * Converts a scala byte value to a json number value.
   * @param value: value to be serialized in json format.
   * @return serialized value.
   */
  def marshal(value: Any): JsValue = {
    value match {
      case byte: Byte => JsNumber(byte)
      case byte: java.lang.Byte => JsNumber(byte.toByte)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerByte: $other.\nMarshallerByte can only be applied to byte values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
