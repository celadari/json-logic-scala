package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.DOUBLE_CODENAME


object MarshallerDouble extends Marshaller {
  val typeCodename: String = DOUBLE_CODENAME
  val typeClassName: String = classOf[java.lang.Double].getName

  def marshal(value: Any): JsValue = {
    value match {
      case doubleValue: Double => JsNumber(doubleValue)
      case doubleValue: java.lang.Double => JsNumber(doubleValue.toDouble)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerDouble: $other.\nMarshallerDouble can only be applied to Double values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
