package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsString, JsValue}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.STRING_CODENAME
import com.github.celadari.jsonlogicscala.serialize.Marshaller
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


object MarshallerString extends Marshaller {
  val typeCodename: String = STRING_CODENAME
  val typeClassName: String = classOf[java.lang.String].getName

  def marshal(value: Any): JsValue = {
    value match {
      case string: String => JsString(string)
      case other => {
        throw new IllegalInputException(s"Illegal input argument to MarshallerString: $other.\nMarshallerString can only be applied to string values." +
          "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
      }
    }
  }
}
