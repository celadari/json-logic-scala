---
title: Evaluate json Scala data structure
author: Charles
nav_order: 5
category: Jekyll
layout: post
parent: Getting started
---

Json-logic-format represents expressions.
The previous pages deal with parsing a json in json-logic-typed format to a Scala data structure and vice versa. 

**This page is about evaluating the represented expression in Scala.** More precisely it is about evaluating a Scala
data structure `JsonLogicCore` representing a json-logic-typed json.


Evaluating a `JsonLogicCore` Scala data structure is an evaluate operation performed by
utility class `EvaluatorLogic`.

* `EvaluatorLogic` is a class responsible for evaluating `JsonLogicCore` into an `Any` value.
Configuration is provided with a `EvaluatorLogicConf` object, if no object is provided the default one is used
instead.
* `EvaluatorValueLogic` is a trait providing abstract method `evaluateValueLogic(value: Any): Any`.
A class implementing this trait will be applied to transform leaf values (in abstract syntax tree) before evaluating.
* `EvaluatorLogicConf` provides mappings from `TypeValue` to `EvaluatorValueLogic` and mappings from operator codename
to the right method to be used for evaluation.


### Evaluation example:
The following expression
$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$
which leads to the following abstract syntax tree
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

### To summarize `EvaluatorValueLogic`
**Evaluation will start by applying the class extending `EvaluatorValueLogic` defined above on all syntax tree nodes leaf
associated with this `TypeValue` (![see explanation on `TypeValue`]({% link _posts/json-logic-typed-scala-representation-part-2.md %}))**

### More detailed code lines

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