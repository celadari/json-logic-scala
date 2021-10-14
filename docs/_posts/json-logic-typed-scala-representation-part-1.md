---
title: "Data structures: Scala representation (part 1)"
author: Charles
nav_order: 2
category: Jekyll
layout: post
parent: Getting started
---

{% include mathjax.html %}

# Scala data structures: global view (part 1/2)

Json-logic-scala lets you represent Scala data structures as typed JsonLogic data.
It also has utilities that let you deserialize the JSON data back into the Scala
data structure.

## Different representations of logic

To help understand how Scala data structures can be serialized into JSON (and deserialized),
you can use the abstract syntax tree to conceptualize expressions.

Consider the following expression:

$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$

This can expression can be represented in an abstract syntax tree:
<p align="center">
    <img src="/assets/boolean_logical_tree.png" alt="drawing" width="500"/>
</p>


In Scala, you could represent this tree with the following data structure:

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
_In this preceding snippit, `PRICE_INT` is a `Int` value and `LABEL_STRING` is
a `String` value.

<!-- NOTE: How about representing this as JSON Logic? -->
<!-- How about Moving this to the intro? -->
