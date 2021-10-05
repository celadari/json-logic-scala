---
title: "Reduce/Unary/Global/Composition operators"
author: Charles
nav_order: 8
category: Jekyll
layout: post
---

## Unary operator

### What is an Unary operator ?

An unary operator is an operator with only one operand.

*Example*
A typical example is the logical negate operator.
```json
[{
  "!": [{"var": "some_variable", "type": {"codename": "boolean"}}]
},
{
  "some_variable": true
}]
```
Previous json should evaluate to `false` because of negate operator.

### How to define a custom Unary operator ?
You need to implement trait `UnaryOperator` and pass it in a `MethodConf` exactly as in ["Add a Custom Operator at Evaluation time"]({% link _posts/evaluator-define-custom-operator.md %}).
Implementing trait `UnaryOperator` only means to implement abstract method `def unaryOperator(value: Any): Any`

*Example*
```scala
object SomeUnaryOperator extends UnaryOperator {
  def unaryOperator(value: Any): Any = ...
}

val methodConf = MethodConf(
  "unary_operator_codename",
  null,
  Some(SomeUnaryOperator),
  false,
  false,
  true
)
...
```


## Reduce operator

### What is a Reduce operator ?

A reduce operator reduces the elements of an array into a single result.

At each step, the reduce sequentially scans an element from the original array and takes two inputs:
* the accumulated result so far
* the current element from array

and updates the accumulated result.

### How to define a Reduce operator ?

There is no constraint on method names to implement.
A reduce operator only needs to implement a method compatible signature with array elements.
Json-Logic-Scala will handle overloaded method as long as overloaded method is not generic.

*Example*:

In the following example, the two times operator has "times2" as a codename and must declare an `Operator`
class/object that defines the computation performed by this operator.

```scala
object Times2Operator extends Operator {
  def method2Times(num1: Double, num2: Double): Double = 2 * num1 * num2
}
```
operator is provided to `EvaluatorLogicConf` through `MethodConf` object.
```scala
val methodConf = MethodConf("times2", "method2Times", Some(Times2Operator), true, false, false)
```
*Make sure to give right `methodName` and `ownerMethodOpt`*

```json
{
  "times2": [
    {"var": "some_value1", "type":  {"codename":  "int"}},
    {"var": "some_value2", "type":  {"codename":  "int"}}
  ]
}
```

## Global operator


### What is a Global operator ?

A Global operator is an operator that is **neither Unary, Reduce, Composition.**

Global operator are operators that are not unary and need to evaluate all input conditions at once
(opposite of reduce operator that evaluate element by element).
Typical examples are: "ifElse", "in", "merge"

### How to define a Global operator ?

There is no constraint on method names to implement.
A global operator only needs to implement a method that takes as single input an Array.
Json-Logic-Scala will handle overloaded method as long as overloaded method is not generic.

*Example*:

In the following example, the sum of modulo 2 operator has "sum_modulo_2" as a codename and must declare an `Operator`
class/object that defines the computation performed by this operator.
This operator returns the sum of odd number is there are more odd numbers in array, sum of even numbers otherwise.

```scala
object SumOfModulo2 extends Operator {
  def methodSumOfModulo2(values: Array[java.lang.Integer]): java.lang.Integer = {
    val numberPerModulo = arr.groupBy(_ % 2).mapValues(_.length)
    if (numberPerModulo(0) > numberPerModulo(1)) values.filter(_ % 2 == 0).sum
    else values.filter(_ % 2 == 1).sum
  }
}

val methodConf = MethodConf(
  "sum_modulo_2",
  "methodSumOfModulo2",
  Some(SumOfModulo2),
  false,
  false,
  false
)
```

## Composition Operator

### What is a Composition operator ?

A composition operator is an operator that takes two inputs
* an array of values
* an operator that shall be applied on the array of values

Common composition operators are: map, filter, reduce, all, some, none

### How to define a Composition operator ?
You need to implement trait `CompositionOperator` and pass it in a `MethodConf` exactly as in ["Add a Custom Operator at Evaluation time"]({% link _posts/evaluator-define-custom-operator.md %}).
Implementing trait `CompositionOperator` means to implement abstract methods:
* `def checkInputs(conditions: Array[JsonLogicCore]): Unit`
* `composeOperator`
```scala
def composeOperator(
  values: Array[Any],
  logicArr: Array[JsonLogicCore],
  conditionCaller: ComposeLogic,
  reduceLogic: EvaluatorLogic,
  logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
): Any
```

#### `checkInputs` method
Verifies there is no error in the input conditions.
**`conditions` input is an array. First element is typically the set of values composition operator is applied to and
the second element is an operator that is applied to it**

#### `composeOperator` method
Performs the logic behind the operator.
* `values`: values operator operates on.
* `logicArr`: array of sub-nodes this composition operator operates one. Most of the time array is of length 1.
* `conditionCaller`: node in syntax tree whose operator is this one.
* `reduceLogic`: evaluator that called this method. Required to pass updated "logicOperatorToValue" recursively to sub-tree and evaluate it.
* `logicOperatorToValue`: map of (composition_operator -> map(variable_name -> variable_value)).
* `operated` value by operator.

*Example*
```scala
object OperatorMap extends CompositionOperator {

  def checkInputs(conditions: Array[JsonLogicCore]): Unit = {
    if (conditions.length != 2) {
      val condString = conditions.mkString("[", ", ", "]")
      throw new WrongNumberOfConditionsException(s"Map operator requires length of condition inputs array to be exactly 2.\nArray of conditions: $condString")
    }
  }

  override def composeOperator(
                                values: Array[Any],
                                logicArr: Array[JsonLogicCore],
                                conditionCaller: ComposeLogic,
                                reduceLogic: EvaluatorLogic,
                                logicOperatorToValue: Map[ComposeLogic, Map[String, Any]]
                              ): Any = {
    val jsonLogicComposition = logicArr(0)

    values.map(value => {
      val newLogicOperatorToValue = logicOperatorToValue ++ Map(conditionCaller -> Map("" -> value))
      reduceLogic.evaluate(jsonLogicComposition, newLogicOperatorToValue)
    })
  }

}
```

**Note that `compositionOperator` makes usage of `reduceLogic` parameter to reapply `EvaluatorLogic.evaluate` method**.
This is necessary in case you compose several composition operators together. In fact, you may want to combine
a map operator with a filter operator. This is why you should consider with great care this when implementing a composition operator.
