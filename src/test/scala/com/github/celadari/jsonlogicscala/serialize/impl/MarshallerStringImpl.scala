package com.github.celadari.jsonlogicscala.serialize.impl

import play.api.libs.json.{JsString, JsValue}
import com.github.celadari.jsonlogicscala.serialize.Marshaller

class MarshallerStringImpl(val prefix: String, val suffix: String) extends Marshaller {

  override def toString: String = this.getClass.getName
  def marshal(value: Any): JsValue = JsString(s"$prefix$value$suffix")
}
