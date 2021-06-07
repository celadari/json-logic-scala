package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException

object UnmarshallerInt extends Unmarshaller{
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
