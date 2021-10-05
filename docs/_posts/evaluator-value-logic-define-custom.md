---
title: "Add a Custom Transformation before Evaluation"
author: Charles
nav_order: 7
category: Jekyll
layout: post
---

{% include mathjax.html %}


## What is `EvaluatorValueLogic` ?

An object implementing `EvaluatorValueLogic` interface is to be called before evaluation
and transforms leaf values in syntax tree.

## Why to use an `EvaluatorValueLogic` ?

The whole idea behind is to enable you to enable custom pre-treatments on a json in
Json-Logic-Typed format.
Imagine you need to share a common json in Json-Logic-Typed format between several
components (frontend/backend/data). 
Imagine now that you would to need to elaborate a custom pre-treatment specific to
each component (frontend/backend/data).

*Example*
Let's suppose you receive a json in Json-Logic-Typed format and one of the leaf nodes
(in the syntax tree) is a string representing a car as: brand + vehicle registration number.

![Original json](assets/non-pretreated-json-logic-tree.png)

imagine all your defined logic and operators need to be applied on vehicle registration number
only. All you'd need is an `EvaluatorValueLogic` to handle this to you.
By doing so, evaluation would not be performed on previous expression (syntax tree) but the
following one.

![Pre-treated json](assets/pretreated-json-logic-tree.png)


## How to define a custom `EvaluatorValueLogic` ?

A custom object implementing `EvaluatorValueLogic` is defined by the Type it is associated
to.

*Example*:
In the previous example, the associated Type with the `EvaluatorValueLogic` implementation object
would be `{"codename": "string"}`.


Information on mapping between Types and `EvaluatorValueLogic` is contained in `EvaluatorLogicConf`.
The last one being given as init parameter to `EvaluatorLogic`.

Information is contained in `EvaluatorLogicConf` object through parameters
`valueLogicTypeToReducerMetaInfAdd` and `valueLogicTypeToReducerManualAdd`.
They are mappings of `Type` to `EvaluatorValueLogic`.


You only need to pass your `EvaluatorValueLogic` mapping object to `EvaluatorLogicConf.createConf` method.
```scala
val myStringEvaluatorValueLogic = ...
val valueLogicTypeToReducerManualAdd = Map(SimpleTypeValue("string") -> myStringEvaluatorValueLogic)
implicit val confEvaluator = EvaluatorLogicConf.createConf(valueLogicTypeToReducerManualAdd=valueLogicTypeToReducerManualAdd)
val evaluator = new EvaluatorLogic
evaluator.eval(...)
```

## How to implement a custom operator ?

Implementing `EvaluatorValueLogic` trait only requires to implement `` method.

*Example:*
Based on the previous example
```scala
object CustomEvaluatorValueLogic extends EvaluatorValueLogic {
  def evaluateValueLogic(value: Any): Any = {
    value match {
      case string: String => ...
      case _ => throw ...
    }
  }
}

val valueLogicTypeToReducerManualAdd = Map(SimpleTypeValue("string") -> CustomEvaluatorValueLogic)
implicit val confEvaluator = EvaluatorLogicConf.createConf(valueLogicTypeToReducerManualAdd=valueLogicTypeToReducerManualAdd)
val evaluator = new EvaluatorLogic
evaluator.eval(...)
```

## Define an `EvaluatorValueLogic`: as a service

Please refer to [Advanced: Define custom object as Java Services]({% link _posts/define-as-a-service.md %})




