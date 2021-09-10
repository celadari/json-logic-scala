package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.SHORT_CODENAME


object MarshallerShort extends Marshaller {
  val typeCodename: String = SHORT_CODENAME
  val typeClassName: String = classOf[java.lang.Short].getName

  def marshal(value: Any): JsValue = {
    value match {
      case short: Short => JsNumber(short)
      case short: java.lang.Short => JsNumber(short.toShort)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerShort: $other.\nMarshallerShort can only be applied to short values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
    JsNumber(value.toString.toShort)
  }
}
