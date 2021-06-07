package com.github.celadari.jsonlogicscala.serialize.defaults

import play.api.libs.json.{JsNull, JsValue}
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.NULL_CODENAME
import com.github.celadari.jsonlogicscala.serialize.Marshaller
import com.github.celadari.jsonlogicscala.exceptions.IllegalInputException


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
