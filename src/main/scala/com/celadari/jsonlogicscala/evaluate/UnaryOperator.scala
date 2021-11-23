// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate


/**
 * Defines interface of class/object that defines an unary operator.
 * Unlike binary|multiple input operators, unary operators take only one input.
 * Invoked method has to be "unaryOperator" and overloading this method doesn't work.
 * @example negate operator, getOrDefaultOperator
 */
trait UnaryOperator extends Operator {

  /**
   * Returns value by this unary operator.
   * @param value: value operator operates on.
   * @return operated value by operator.
   */
  def unaryOperator(value: Any): Any
}
