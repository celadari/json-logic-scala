---
title: "Add a Custom Operator at Evaluation time"
author: Charles
nav_order: 6
category: Jekyll
layout: post
has_children: true
---

## What is Evaluation time ?

The whole idea behind Json-Logic-Typed is to represent expressions in interoperable format
to be exchanged between different actors (typically frontend and backend).

Such json data format enhances conveying information across different platforms/languages.
Such platforms/languages in turn evaluate the underlying expression on its own.

**Evaluation "evaluates" the underlying expression to provide you a result.**

## How to define a custom operator ?

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

There are two approaches: manual and as a service.



## How to implement a custom operator ?

There are two distinct ways of defining an operator.

### First approach: implementing `Operator` trait




