---
title: Evaluate json Scala data structure
author: Charles
nav_order: 5
category: Jekyll
layout: post
parent: Getting started
---
{% include mathjax.html %}

# Use json-logic-scala to evaluate expressions

The previous pages cover [parsing a typed version of JsonLogic into a Scala data
structure](parse-json-logic-typed), and the [reverse process of serialization](serialize-json-logic-typed).

In all these cases, the underlying data might be an _expression_ that you want to
evaluate. json-logic-scala provides utilities to evaluate these expressions.
This section goes over how to do this.
More precisely, this section is about evaluating the [Scala data structure `JsonLogicCore`](json-logic-typed-scala-representation-part-2),
which represents json-logic-typed JSON.

## The `EvaluatorLogic` class

To evaluate a `JsonLogicCore` Scala data structure, use the  `EvaluatorLogic`
utility class.

* The `EvaluatorLogic` class is responsible for evaluating `JsonLogicCore` into an `Any` value.
Configuration is provided with a `EvaluatorLogicConf` object. If no `EvaluatorLogicConf`
object is provided, the default one is used.
* `EvaluatorValueLogic` is a trait that provides the abstract method `evaluateValueLogic(value: Any): Any`.
A class implementing this trait will be applied to transform leaf values (in an abstract syntax tree) before evaluating.
* `EvaluatorLogicConf` provides mappings from `TypeValue` to `EvaluatorValueLogic`,
and mappings from the operator codename to the right method to be used for evaluation.

### Evaluation example:
The following expression

$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$
$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$

leads to the following abstract syntax tree
<p align="center">
    <img src="/assets/boolean_logical_tree.png" alt="drawing" width="500"/>
</p>

if evaluated with the following `EvaluatorValueLogic`

```scala
class IncrementLeafInt(intToBeAdded: Int) extends EvaluatorValueLogic {
  def evaluateValueLogic(value: Any): Any = {
    value match {
      case int: Int => int + intToBeAdded
      case any => throw new IllegalArgumentException(...)
    }
  }
}
val incrementLeafInt = new IncrementLeafInt(2)
```
is equivalent to an evaluation on the following abstract tree:
<p align="center">
    <img src="/assets/boolean_logical_tree_applied_evaluatorvaluelogic.png" alt="drawing" width="500"/>
</p>

### Summary: how `EvaluatorValueLogic` evaluates

Evaluation starts by applying the class extending `EvaluatorValueLogic`
on all syntax tree node leaves that are associated with this `TypeValue`
([see the explanation on `TypeValue`](_posts/json-logic-typed-scala-representation-part-2.md))

### A detailed example of code using EvaluatorValueLogic

```scala
class IncrementLeafInt(intToBeAdded: Int) extends EvaluatorValueLogic {
  def evaluateValueLogic(value: Any): Any = {
    value match {
      case int: Int => int + intToBeAdded
      case any => throw new IllegalArgumentException(...)
    }
  }
}
val incrementLeafInt = new IncrementLeafInt(2)

val evaluatorLogicConf = EvaluatorLogicConf
  .createConf(evaluatorValueLogicManualAdd = Map(INT_CODENAME -> incrementLeafInt))
}
val evaluatorLogic = new EvaluatorLogic(evaluatorLogicConf)

val jsonLogicCore: JsonLogicCore = ...

evaluatorLogic.eval(jsonLogicCore)
```
