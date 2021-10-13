---
title: "Custom Marshaller"
author: Charles
nav_order: 2
category: Jekyll
layout: post
parent: Marshall and Unmarshall
---

Json Logic Scala comes a built-in `Serializer` that handles a number of
[pre-defined Types]({% link _posts/concept-of-types.md %}).

You may want to serialize more Types than those predefined by the library. For example,
you may want to serialize a custom class you have implemented.

## What is a Marshaller ?

A marshaller is an object that converts a value from a Json-Logic-Typed
(leaf node in syntax tree) into a Scala data type.

A marshaller is defined by trait `Marshaller` and must implement the method:

```scala
def marshal(value: Any): JsValue
```

Each Type is associated with its own `Marshaller`.

*Example*:
If you define a custom class A, you may define its `Marshaller`:

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

A `Serializer` converts a Scala data structure `JsonLogicScala` into a json in Json-Logic-Typed format.

A `Serializer` parses a json following the Json-Logic-Typed format and to every found Type
relies on its associated `Marshaller`.

The association between Type and `Marshaller` is provided in the `SerializerConf` object.
