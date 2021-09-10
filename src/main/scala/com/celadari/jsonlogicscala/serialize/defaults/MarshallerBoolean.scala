package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsBoolean, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.BOOL_CODENAME


object MarshallerBoolean extends Marshaller {
  val typeCodename: String = BOOL_CODENAME
  val typeClassName: String = classOf[java.lang.Boolean].getName

  def marshal(value: Any): JsValue = {
    value match {
      case boolean: Boolean => JsBoolean(boolean)
      case bool: java.lang.Boolean => JsBoolean(bool)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerBoolean: $other.\nMarshallerBoolean can only be applied to boolean values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
