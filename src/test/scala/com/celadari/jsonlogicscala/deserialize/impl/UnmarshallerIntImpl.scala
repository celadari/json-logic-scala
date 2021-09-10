package com.celadari.jsonlogicscala.deserialize.impl

import play.api.libs.json.JsValue
import com.celadari.jsonlogicscala.deserialize.Unmarshaller


object UnmarshallerIntImpl extends Unmarshaller {

  override def toString: String = this.getClass.getName
  override def unmarshal(jsValue: JsValue): Any = jsValue.as[Int]
}
