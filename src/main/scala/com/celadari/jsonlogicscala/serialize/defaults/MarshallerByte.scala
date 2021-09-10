package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.BYTE_CODENAME


object MarshallerByte extends Marshaller {
  val typeCodename: String = BYTE_CODENAME
  val typeClassName: String = classOf[java.lang.Byte].getName

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
