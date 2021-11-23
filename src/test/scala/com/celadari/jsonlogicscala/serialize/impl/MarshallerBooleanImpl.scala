// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize.impl

import play.api.libs.json.{JsBoolean, JsValue}
import com.celadari.jsonlogicscala.serialize.Marshaller


object MarshallerBooleanImpl extends Marshaller {

  override def toString: String = this.getClass.getName
  def marshal(value: Any): JsValue = JsBoolean(value.toString.toBoolean)
}
