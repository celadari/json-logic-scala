package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNumber, JsValue}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.SHORT_CODENAME
import com.github.celadari.jsonlogicscala.serialize.Marshaller
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


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
