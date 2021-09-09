package com.github.celadari.jsonlogicscala.serialize.impl

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.serialize.Marshaller

object MarshallerIntImpl extends Marshaller {

  override def toString: String = this.getClass.getName
  def marshal(value: Any): JsValue = JsNumber(value.toString.toInt)
}