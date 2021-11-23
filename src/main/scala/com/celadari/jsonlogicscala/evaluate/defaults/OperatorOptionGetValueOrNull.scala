// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate.defaults

import com.celadari.jsonlogicscala.evaluate.UnaryOperator


object OperatorOptionGetValueOrNull extends UnaryOperator {

  /**
   * Returns value from Option.
   * Returns value itself if value is not a Option[_].
   * @param value: value operator operates on.
   * @return value.
   */
  def unaryOperator(value: Any): Any = {
    value match {
      case opt: Option[_] => opt.orNull
      case other => other
    }
  }
}
