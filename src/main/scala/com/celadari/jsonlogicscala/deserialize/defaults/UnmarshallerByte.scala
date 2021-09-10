package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerByte extends Unmarshaller{
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
