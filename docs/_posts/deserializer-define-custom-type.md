---
title: "Custom Unmarshaller"
author: Charles
nav_order: 3
category: Jekyll
layout: post
parent: Marshall and Unmarshall
---

Json Logic Scala comes a built-in `Deserializer` that handles a number of
[pre-defined Types]({% link _posts/concept-of-types.md %}).

You may want to parse more Types than those predefined by the library. For example,
you may want to parse a custom class that you have implemented.

## What is an Unmarshaller ?

An unmarshaller is an object that converts a value from Json-Logic-Typed JSON
(i.e. a leaf node in syntax tree) to a Scala data structure `JsonLogicCore`.

An unmarshaller is defined by the trait `Unmarshaller` and must implement the
method

```scala
def unmarshal(jsValue: JsValue): Any
```

Each Type is associated with its own `Unmarshaller`.

**Example**:
If you define the custom class A, you may define its `Unmarshaller`

```scala
class A(val param1: String, val param2: Int)

object UnmarshallerA {
  def unmarshal(jsValue: JsValue): Any = {
    val param1 = (jsValue \ "param1").as[String]
    val param2 = (jsValue \ "param2").as[String]
    new A(param1, param2)
  }
}
```

## What is a Deserializer ?

A `Deserializer` parses JSON in Json-Logic-Typed format into a Scala data structure `JsonLogicScala`.

A `Deserializer` follows the Json-Logic-Typed format to parse JSON.
For every found Type, the operation relies on its associated `Unmarshaller`.

The association between Type and `Unmarshaller` is provided in the `DeserializerConf` object.

