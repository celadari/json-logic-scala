package com.celadari.jsonlogicscala.serialize.impl

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.serialize.Marshaller


class MarshallerDoubleImpl(offset: Double) extends Marshaller {

  override def toString: String = this.getClass.getName
  def marshal(value: Any): JsValue = {
    JsNumber(value.toString.toDouble + offset)
  }
}
