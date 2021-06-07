package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.FLOAT_CODENAME
import com.github.celadari.jsonlogicscala.serialize.Marshaller
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


object MarshallerFloat extends Marshaller {
  val typeCodename: String = FLOAT_CODENAME
  val typeClassName: String = classOf[java.lang.Double].getName

  def marshal(value: Any): JsValue = {
    value match {
      case floatValue: Float => JsNumber(floatValue)
      case floatValue: java.lang.Float => JsNumber(floatValue.toFloat)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerFloat: $other.\nMarshallerFloat can only be applied to Float values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
