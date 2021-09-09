package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerShort extends Unmarshaller{

  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toShort
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerShort: $other.\nUnmarshallerShort could not unmarshall to Short " +
          "value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}