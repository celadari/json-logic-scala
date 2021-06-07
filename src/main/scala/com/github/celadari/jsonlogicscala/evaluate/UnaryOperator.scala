package com.github.celadari.jsonlogicscala.evaluate

trait UnaryOperator extends Operator {

  def unaryOperator(value: Any): Any
}
