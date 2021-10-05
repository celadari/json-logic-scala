---
title: "Add a Custom Type to Deserializer (parsing)"
author: Charles
nav_order: 5
category: Jekyll
layout: post
---

Json Logic Scala comes a built-in `Deserializer` that handles a number of
[pre-defined Types]({% link _posts/concept-of-types.md %}).

You may want to parse more Types than those predefined by the library. For example,
you may want to parse a custom class you have implemented.

**Json Logic Scala relies on the Play-Json library** You may need to read the
Play-Json documentation (or at least understand the following classes/traits 
`JsValue`, `JsObject`).

## What is an Unmarshaller ?

An unmarshaller is an object that converts a value from a json in Json-Logic-Typed
(leaf node in syntax tree) format to a scala data structure `JsonLogicCore`.

An unmarshaller is defined by trait `Unmarshaller` and must implement method

```scala
def unmarshal(jsValue: JsValue): Any
```

Each Type is associated with its own `Unmarshaller`.

*Example*:
If you define custom class A, you may define its `Unmarshaller`
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

A `Deserializer` parses a json in Json-Logic-Typed format into a scala data structure `JsonLogicScala`.

A `Deserializer` parses a json following the Json-Logic-Typed format and to every found Type
relies on its associated `Unmarshaller`.
Association between Type and `Unmarshaller` is provided in `DeserializerConf` object.

