package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.LONG_CODENAME


object MarshallerLong extends Marshaller {
  val typeCodename: String = LONG_CODENAME
  val typeClassName: String = classOf[java.lang.Long].getName

  def marshal(value: Any): JsValue = {
    value match {
      case longValue: Long => JsNumber(longValue)
      case longValue: java.lang.Long => JsNumber(longValue.toLong)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerLong: $other.\nMarshallerLong can only be applied to Long values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
