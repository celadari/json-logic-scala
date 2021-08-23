---
title: "Data structures: scala representation (first view)"
author: Charles
nav_order: 2
category: Jekyll
layout: post
parent: Getting started
---
{% include mathjax.html %}

# Scala data structures: first approach

Json-logic-typed contains data structures to represent json-logic-typed data and utilities to make
conversion between data and data representation.

**Scala data structures are abstract syntax tree based.** (see example below)

* A `JsonLogicCore` object is a scala representation of a json-logic-typed datum. Interface exposes one 
attribute `operator` of type String.
* A `ValueLogic` - subtype of `JsonLogicCore` - object is a scala representation of a data-node in
json-logic-typed datum. It consists of several attributes, attributes in the scope of this doc-page ():
`operator` of type String and `valueOpt` of generic-type Option[T].
* A `ComposeLogic` - subtype of `JsonLogicCore` - object is a scala representation of an operator-node in json-logic-typed datum.
It consists of two attributes: `operator` of type String and `conditions` of type Array[JsonLogicCore].

### For example, the following expression:

$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$

leads to the following Abstract Syntax Tree
<p align="center">
    <img src="/assets/boolean_logical_tree.png" alt="drawing" width="500"/>
</p>

which would be represented in scala by the following data structure

```scala
new ComposeLogic("and", Array(
  new ComposeLogic(">=", Array(
    ValueLogic(Some(PRICE_INT), ...),
    ValueLogic(Some(20), ...)
  )),
  new ComposeLogic("!=", Array(
    ValueLogic(Some(LABEL_STRING), ...),
    ValueLogic(Some("label2"), ...)
  ))
))
```
where $$PRICE\_INT$$ and $$LABEL\_STRING$$ are Int and String values.

