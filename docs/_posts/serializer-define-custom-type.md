---
title: "Add a Custom Type to Serializer (Unparsing)"
author: Charles
nav_order: 4
category: Jekyll
layout: post
has_children: true
---

Json Logic Scala comes a built-in `Serializer` that handles a number of
[pre-defined Types]({% post_url concept-of-types %}).

You may want to serialize more Types than those predefined by the library. For example,
you may want to serialize a custom class you have implemented.

**Json Logic Scala relies on the Play-Json library** You may need to read the
Play-Json documentation (or at least understand the following classes/traits 
`JsValue`, `JsObject`).

## What is a Marshaller ?

A marshaller is an object that converts a value from a Json-Logic-Typed
(leaf node in syntax tree) into a scala data type.

A marshaller is defined by trait `Marshaller` and must implement method

```scala
def marshal(value: Any): JsValue
```

Each Type is associated with its own `Marshaller`.

*Example*:
If you define custom class A, you may define its `Marshaller`
```scala
class A(val param1: String, val param2: Int)

object UnmarshallerA {
  def marshal(value: Any): JsValue = {
    value match {
      case a: A => JsObject(Map("param1" -> JsString(a.param1), "param2" -> JsNumber(a.param2)))
      case _ => throw ...
    }
  }
}
```

## What is a Serializer ?

A `Serializer` converts a scala data structure `JsonLogicScala` into a json in Json-Logic-Typed format.

A `Serializer` parses a json following the Json-Logic-Typed format and to every found Type
relies on its associated `Marshaller`.
Association between Type and `Marshaller` is provided in `SerializerConf` object.
