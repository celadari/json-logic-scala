// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.impl

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.serialize.Marshaller


object MarshallerIntImpl extends Marshaller {

  override def toString: String = this.getClass.getName
  def marshal(value: Any): JsValue = JsNumber(value.toString.toInt)
}
