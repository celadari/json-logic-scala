package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerFloat extends Unmarshaller{
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toFloat
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerFloat: $other.\nUnmarshallerFloat could not unmarshall " +
          "to Float value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
