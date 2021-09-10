package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsString, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.STRING_CODENAME


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
