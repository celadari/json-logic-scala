package com.github.celadari.jsonlogicscala.deserialize.impl

import play.api.libs.json.JsValue
import com.github.celadari.jsonlogicscala.deserialize.Unmarshaller

class UnmarshallerStringImpl(val prefix: String, val suffix: String) extends Unmarshaller {

  override def toString: String = this.getClass.getName
  override def unmarshal(jsValue: JsValue): Any = s"$prefix${jsValue.as[String]}$suffix"
}
