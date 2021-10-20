---
title: Evaluate json Scala data structure
author: Charles
nav_order: 590
category: Jekyll
layout: post
parent: "Parse, Serialize, Evaluate"
---
{% include mathjax.html %}

A central goal of Json-Logic-Typed is to represent expressions in interoperable format.

This allows the expressions to be exchanged between different actors
(typically the frontend and backend).

Such a JSON data format enhances how you can convey information across
different platforms and languages.
These platforms can in turn evaluate the underlying expression on their own.

**Evaluation "evaluates" the underlying expression to provide you a result.**
# Use json-logic-scala to evaluate expressions

The previous pages cover [parsing a typed version of JsonLogic into a Scala data
structure](parse-json-logic-typed), and the [reverse process of serialization](serialize-json-logic-typed).

In all these cases, the underlying data might be an _expression_ that you want to
evaluate. json-logic-scala provides utilities to evaluate these expressions.
This section goes over how to do this.

More precisely, this section is about evaluating the [Scala data structure `JsonLogicCore`](scala-data-structures-global-view),
which represents JsonLogic-Typed JSON.

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

Consider the following expression:

$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$
$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$

It is represented by the following abstract syntax tree:

<p align="center">
    <img src="/assets/boolean_logical_tree.png" alt="drawing" width="500"/>
</p>


If evaluated with the following `EvaluatorValueLogic`

```scala
class IncrementLeafInt(intToBeAdded: Int) extends EvaluatorValueLogic {
  def evaluateValueLogic(value: Any): Any = {
    value match {
      case int: Int => if (int < 20) int - intToBeAdded else int + intToBeAdded
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
([see the explanation on `TypeValue`](scala-data-structures-global-view#the-typevalue-object-represents-a-value-type)

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
