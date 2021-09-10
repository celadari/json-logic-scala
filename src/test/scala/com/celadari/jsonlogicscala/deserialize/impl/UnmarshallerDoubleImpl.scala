package com.celadari.jsonlogicscala.deserialize.impl

import play.api.libs.json.JsValue
import com.celadari.jsonlogicscala.deserialize.Unmarshaller


class UnmarshallerDoubleImpl(offset: Double) extends Unmarshaller {

  override def toString: String = this.getClass.getName
  override def unmarshal(jsValue: JsValue): Any = jsValue.as[Double] + offset
}
