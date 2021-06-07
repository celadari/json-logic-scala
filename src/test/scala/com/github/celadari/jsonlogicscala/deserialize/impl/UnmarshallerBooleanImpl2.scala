package com.github.celadari.jsonlogicscala.deserialize.impl

import play.api.libs.json.JsValue
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller

object UnmarshallerBooleanImpl2 extends Unmarshaller {

  override def toString: String = this.getClass.getName
  override def unmarshal(jsValue: JsValue): Any = jsValue.as[Boolean]
}
