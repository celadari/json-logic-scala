package com.github.celadari.jsonlogicscala.evaluate.defaults

import com.github.celadari.jsonlogicscala.evaluate.Operator

object OperatorOr extends Operator {

  def $bar$bar(bool1: java.lang.Boolean, bool2: java.lang.Boolean): java.lang.Boolean = bool1 || bool2
}