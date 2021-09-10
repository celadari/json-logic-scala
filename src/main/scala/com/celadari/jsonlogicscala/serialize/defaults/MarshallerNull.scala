package com.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNull, JsValue}
import com.celadari.jsonlogicscala.exceptions.IllegalInputException
import com.celadari.jsonlogicscala.serialize.Marshaller
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.NULL_CODENAME


object MarshallerNull extends Marshaller {
  val typeCodename: String = NULL_CODENAME
  val typeClassName: String = classOf[Null].getName

  def marshal(value: Any): JsValue = {
    if (value == null) JsNull
    else {
      throw new IllegalInputException(s"Illegal input argument to MarshallerNull: $value.\nMarshallerNull can only be applied to null value." +
        "\nCheck if valueOpt and typeCodenameOpt of ValueLogic are correct.")
    }
  }
}
