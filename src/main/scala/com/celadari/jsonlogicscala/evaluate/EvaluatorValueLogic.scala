// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate


/**
 * Companion object to hold default [[com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic]].
 */
object EvaluatorValueLogic {

  /**
   * Default [[com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic]].
   * Identity returns input.
   */
  val IDENTITY_REDUCER_VALUE_LOGIC: EvaluatorValueLogic = new EvaluatorValueLogic {
    override def evaluateValueLogic(value: Any): Any = value
  }
}

/**
 * Defines interface of class/object that defines an evaluator_value_logic.
 * A class|object implementing this interface is called before evaluation and transforms leaf values in syntax tree.
 *
 *                AND
 *             /       \
 *           <         <
 *         /   \      / \
 *        A     5    B  -4
 *
 * if you define an evaluator_value_logic defined for int values and returns absolute value then evaluation will be on
 * the following tree
 *
 *                AND
 *             /       \
 *           <         <
 *         /   \      / \
 *        A     5    B   4
 */
trait EvaluatorValueLogic {

  /**
   * Returns value by this evaluator_value_logic.
   * @param value: value to be transformed before evaluation time.
   * @return transformed value.
   */
  def evaluateValueLogic(value: Any): Any
}
