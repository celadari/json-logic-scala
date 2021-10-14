---
title: "Custom Operator"
author: Charles
nav_order: 6
category: Jekyll
layout: post
---

{% include mathjax.html %}


## What is Evaluation ?

A central goal of Json-Logic-Typed is to represent expressions in interoperable format.
This allows the expressions to be exchanged between different actors
(typically the frontend and backend).

Such a JSON data format enhances how you can convey information across
different platforms and languages.
These platforms can in turn evaluate the underlying expression on their own.

**Evaluation "evaluates" the underlying expression to provide you a result.**

## How to manually define a custom operator ?

A _custom operator_ is defined by its `codename`, which is the key in
Json-Logic-Typed format.

*Example*:

In the following example, the plus operator has `+` as a codename and must declare
an `Operator` class/object that defines the computation performed by this operator.

```json
{
  "+": [
    {"var": "some_value1", "type":  {"codename":  "int"}},
    {"var": "some_value2", "type":  {"codename":  "int"}}
  ]
}
```

`EvaluatorLogicConf` contains the information on how the implementation associates
a class or object to a `+` operator codename, with the last one being given as
init parameter to `EvaluatorLogic`.

Information provided to `EvaluatorLogicConf` is a mapping of `MethodConf` objects.
`MethodConf` holds an operator's information for evaluation. There are 6 parameters:
* `operator`: `String` codename.
* `methodName`: `String` name of method to invoke at evaluation.
* `ownerMethodOpt`: `Option[Operator]` Optional object. If defined, describes what the
method to be invoked belongs to.
If not defined, it means the method belongs to accumulator at evaluation.
* `isReduceTypeOperator`: `Boolean` true if operator is a reduce type (max, min, +, -, *, /, And, Or, ...), false otherwise.
* `isCompositionOperator`: `Boolean` true if operator is a composition type (reduce, map, filter, all, none, some), false otherwise.
* `isUnary`: `Boolean` true if unary operator (logical negate, ...), false otherwise.

You need to pass only your `MethodConf` object to `EvaluatorLogicConf.createConf` method.

```scala
val methodConf = MethodConf(...)
implicit val confEvaluator = EvaluatorLogicConf.createConf(methodConfsManualAdd = Map("expN" -> confMethod))
val evaluator = new EvaluatorLogic
evaluator.eval(...)
```

## How to implement a custom operator

There are two distinct ways to define an operator.

### First approach: implementing the `Operator` trait

*Example:*
Let's implement an operator "expN" that takes two input $$X$$, $$N$$ and computes:
$$\sum_{n=1}^{N} \frac{X^{n}}{n!}$$

The only thing left to do is manually pass the information about the
"expN" operator as an `MethodConf` object to the `EvaluatorLogicConf.createConf`
method.

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


**Example**
You may also define the operator `secondAcc`, whose method belongs to evaluation accumulator

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
*Notice that method is not defined inside the Operator object, but inside accumulator's class `A`*

## Define an operator: as a service

Please refer to [Advanced: Define a Marshaller/Unmarshaller/Operator as a Java Service]({% link _posts/define-as-a-service.md %})




