---
title: "Add a Custom Operator at Evaluation time"
author: Charles
nav_order: 6
category: Jekyll
layout: post
---

{% include mathjax.html %}


## What is Evaluation ?

The whole idea behind Json-Logic-Typed is to represent expressions in interoperable format
to be exchanged between different actors (typically frontend and backend).

Such json data format enhances conveying information across different platforms/languages.
Such platforms/languages in turn evaluate the underlying expression on its own.

**Evaluation "evaluates" the underlying expression to provide you a result.**

## How to manually define a custom operator ?

A custom operator is defined by its "codename" which is the key in Json-Logic-Typed format.

*Example*:

In the following example, the plus operator has "+" as a codename and must declare an `Operator`
class/object that defines the computation performed by this operator.
```json
{
  "+": [
    {"var": "some_value1", "type":  {"codename":  "int"}},
    {"var": "some_value2", "type":  {"codename":  "int"}}
  ]
}
```

Information on which implemented class/object to associate to "+" operator codename is contained in `EvaluatorLogicConf`.
The last one being given as init parameter to `EvaluatorLogic`.

Information provided to `EvaluatorLogicConf` is a mapping of `MethodConf` objects.
`MethodConf` holds operator's information for evaluation. There are 6 parameters:
* `operator`: `String` codename.
* `methodName`: `String` name of method to invoke at evaluation.
* `ownerMethodOpt`: `Option[Operator]` optional object the method to be invoked belongs to if defined.
If not defined, it means the method belongs to accumulator at evaluation.
* `isReduceTypeOperator`: `Boolean` true if operator is a reduce type (max, min, +, -, *, /, And, Or, ...), false otherwise.
* `isCompositionOperator`: `Boolean` true if operator is a composition type (reduce, map, filter, all, none, some), false otherwise.
* `isUnary`: `Boolean` true if unary operator (logical negate, ...), false otherwise.

You only need to pass your `MethodConf` object to `EvaluatorLogicConf.createConf` method.
```scala
val methodConf = MethodConf(...)
implicit val confEvaluator = EvaluatorLogicConf.createConf(methodConfsManualAdd = Map("expN" -> confMethod))
val evaluator = new EvaluatorLogic
evaluator.eval(...)
```

## How to implement a custom operator ?

There are two distinct ways of defining an operator.

### First approach: implementing `Operator` trait

*Example:*
Let's implement an operator "expN" that takes two input $X$, $N$ and computes:
$$\sum_{n=1}^{N} \frac{X^{n}}{n!}$$

Only remaining thing is to do is manually passing information about "expN" operator as an `MethodConf` object to
`EvaluatorLogicConf.createConf` method.
```scala
class OperatorPlus2 {
  def factorial(n: Int): Int = ...

  def computePartialSum(x: Double, N: Long): Double = {
    var sum = 0
    for (i <- 0 to N) {
      sum += math.pow(x, n) / factorial(n)
    }
    sum
  }
}
val operatorPlus2 = new OperatorPlus2

val confMethod = ConfMethod(
  "expN",
  "computePartialSum",
  Some(operatorPlus2),
  false,
  false,
  false
)

implicit val confEvaluator = EvaluatorLogicConf.createConf(methodConfsManualAdd = Map("expN" -> confMethod))
val evaluator = new EvaluatorLogic
```

evaluating the following json
```json
[{
  "expN": [
    {"var":  "some_variable1", "type":  {"codename":  "int"}},
    {"var":  "some_variable2", "type":  {"codename":  "int"}}
  ]
},
{
  "some_variable1": ...
  "some_variable2": ...
}]
```
can be done using the evaluator:
```scala
val serializer = ...
val jsonLogicCore = serializer.serialize(json)

evalutor.eval(jsonLogicCore)
```

### Second approach: method is defined in accumulator's class definition


*Example*
You may also define operator "secondAcc" whose method belongs to evaluation accumulator
```scala
class A(num1: Int, num2: Double) {
  def combine(that: A): A = new A(num1 + that.num1, num2 + that.num1 * that.num2)
}

val confMethod = ConfMethod(
  "secondAcc",
  "combine",
  None,
  true,
  false,
  false
)

implicit val confEvaluator = EvaluatorLogicConf.createConf(methodConfsManualAdd = Map("secondAcc" -> confMethod))
val evaluator = new EvaluatorLogic
```
*Notice that method is not defined inside ~~Operator~~ object but inside accumulator's class `A`*

## Define an operator: as a service

Please refer to [Advanced: Define a Marshaller/Unmarshaller/Operator as a Java Service]({% link _posts/define-as-a-service.md %})




