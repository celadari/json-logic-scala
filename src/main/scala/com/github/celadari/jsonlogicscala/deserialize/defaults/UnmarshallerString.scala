package com.github.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsString, JsValue}
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.github.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException

object UnmarshallerString extends Unmarshaller{
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsString(string) => string
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerString: $other.\nUnmarshallerString could not unmarshall to " +
          "String value.\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
