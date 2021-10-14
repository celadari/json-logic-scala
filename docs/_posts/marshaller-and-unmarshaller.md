---
title: Marshall and Unmarshall
author: Charles
nav_order: 4
category: Jekyll
layout: post
has_children: true
---

Json-logic-scala includes a few related objects for serializing and deserializing
JSON.

## About these terms

While the terms _marshall_, _serialize_, _unmarshall_, and _deserialize_ are closely
related, they each have different functions. Consider the following objects
in Json-logic-scala:

* `Marshaller` transforms a Scala data structure into a _JSON value_

* `Serializer` transformas a Scala data structure into a JSON representation

The `Unmarshaller` and `Deserializer` objects perform the reverse processes:

* `Unmarshaller` transforms a JSON value to a Scala data structure

* `Deserializer` parses JSON in json-logic-typed format into the Scala data structure
`JsonLogicCore`, which corresponds to a leaf node in the abstract syntax tree.
    * To parse values in leaf nodes, `Deserializer` relies on several `Unmarshaller` objects

## Note on custom implementations

**Json Logic Scala relies on the Play-Json library**

To implement custom marshall and unmarshall operations, you may need to read the
Play-Json documentation (or at least understand the classes/traits described
in these sections: `JsValue`, and `JsObject`).
