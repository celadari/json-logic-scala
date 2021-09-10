package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerDouble extends Unmarshaller{
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNumber(num) => num.toDouble
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerDouble: $other.\nUnmarshallerDouble could not unmarshall " +
          "to Double value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
