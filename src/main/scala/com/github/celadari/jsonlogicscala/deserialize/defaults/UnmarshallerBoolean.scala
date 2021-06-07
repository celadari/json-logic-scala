package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsBoolean, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerBoolean extends Unmarshaller {
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsBoolean(bool) => bool
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerBoolean: $other.\nUnmarshallerBoolean could not unmarshall " +
          "to boolean value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
