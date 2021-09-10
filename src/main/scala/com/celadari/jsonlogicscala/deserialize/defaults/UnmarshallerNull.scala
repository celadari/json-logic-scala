package com.celadari.jsonlogicscala.deserialize.defaults

import play.api.libs.json.{JsNull, JsValue}
import com.celadari.jsonlogicscala.deserialize.Unmarshaller
import com.celadari.jsonlogicscala.exceptions.InvalidJsonParsingException


object UnmarshallerNull extends Unmarshaller {

  // scalastyle:off null
  def unmarshal(jsValue: JsValue): Any = {
    jsValue match {
      case JsNull => null
      case other => {
        throw new InvalidJsonParsingException(s"Illegal input argument to UnmarshallerNull: $other.\nUnmarshallerNull can only be applied to null value." +
          "\nCheck if \"type\" and \"var\" are correct.")
      }
    }
  }
}
