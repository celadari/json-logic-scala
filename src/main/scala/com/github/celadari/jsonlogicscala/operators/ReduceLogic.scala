package com.github.celadari.jsonlogicscala.operators

import com.github.celadari.jsonlogicscala.core.{ComposeLogic, JsonLogicCore, ValueLogic}

object ReduceLogic {

  implicit val reduceLogic: ReduceLogic = new ReduceLogic
}

class ReduceLogic(implicit cmpOp: CompareOperator, ctnOp: ContainsOperator, boolOp: BooleanOperator) {

  def reduceComposeLogic(condition: ComposeLogic): Any = {
    condition.operator match {
      case ComposeLogic.BINARY_OPERATORS.GTEQ => cmpOp.gteq(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.BINARY_OPERATORS.GT => cmpOp.gt(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.BINARY_OPERATORS.LTEQ => cmpOp.lteq(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.BINARY_OPERATORS.LT => cmpOp.lt(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.MULTIPLE_OPERATORS.AND => condition.conditions.tail.foldLeft(reduce(condition.conditions.head)){case (result, cond2) => boolOp.and(result, reduce(cond2))}
      case ComposeLogic.MULTIPLE_OPERATORS.OR=> condition.conditions.tail.foldLeft(reduce(condition.conditions.head)){case (result, cond2) => boolOp.or(result, reduce(cond2))}
      case ComposeLogic.MULTIPLE_OPERATORS.XOR => boolOp.xor(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.BINARY_OPERATORS.EQ => cmpOp.eq(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.BINARY_OPERATORS.DIFF => cmpOp.diff(reduce(condition.conditions(0)), reduce(condition.conditions(1)))
      case ComposeLogic.BINARY_OPERATORS.IN => ctnOp.contains(reduce(condition.conditions(1)), reduce(condition.conditions(0)))
      case ComposeLogic.BINARY_OPERATORS.NOT_IN => ctnOp.containsNot(reduce(condition.conditions(1)), reduce(condition.conditions(0)))
    }
  }

  def reduceValueLogic(condition: ValueLogic[_]): Any = condition.value

  def reduce(condition: JsonLogicCore): Any = {
    condition match {
      case composeLogic: ComposeLogic => reduceComposeLogic(composeLogic)
      case valueLogic: ValueLogic[_] => reduceValueLogic(valueLogic)
      case other => throw new IllegalArgumentException(s"Invalid argument: $other")
    }

  }

}
