---
title: "Custom marshaller and Unmarshaller"
author: Charles
nav_order: 610
category: Jekyll
layout: post
parent: Customize classes
---

Json-logic-scala provides classes that perform operations that _serialize_,
_marshall,_ _deserialize,_ and _unmarshall_ data.

You can modify these classes for your own workflow.

**Table of Contents**

- [About these terms](#about-these-terms)
- [Note on custom implementations](#note-on-custom-implementations)
- [The marshaller and serializer](#the-marshaller-and-serializer)
    - [Build a custom Marshaller](#build-a-custom-marshaller)
    - [Build a custom serializer](#build-a-custom-serializer)
- [The unmarshaller and deserializer](#the-unmarshaller-and-deserializer)
    - [What is an Unmarshaller ?](#what-is-an-unmarshaller-)
    - [What is a Deserializer ?](#what-is-a-deserializer-)

<!-- markdown-toc end -->

## About these terms

While the terms _marshall_, _serialize_, _unmarshall_, and _deserialize_ are closely
related, they each have different functions. Consider the following objects
in Json-logic-scala:

* `Marshaller` transforms a Scala data structure into a _JSON value_

* `Serializer` transforms a Scala data structure into a JSON representation

The `Unmarshaller` and `Deserializer` objects perform the reverse processes:

* `Unmarshaller` transforms a JSON value to a Scala data structure

* `Deserializer` parses JSON in JsonLogic-Typed format into the Scala data structure
`JsonLogicCore`, which corresponds to a leaf node in the abstract syntax tree.
    * To parse values in leaf nodes, `Deserializer` relies on several `Unmarshaller` objects

## Note on custom implementations

> &#128712;
> Json Logic Scala relies on the [Play-Json library](https://github.com/playframework/play-json).

To implement custom marshall and unmarshall operations, you may need to read the
Play-Json documentation (or at least understand the classes/traits described
in these sections: `JsValue`, and `JsObject`).

## The marshaller and serializer

Json Logic Scala comes a built-in `Serializer` that handles a number of
[pre-defined Types]({% link _posts/concept-of-types.md %}).

You may want to serialize more Types than those predefined by the library. For example,
you may want to serialize a custom class you have implemented.

### Build a custom Marshaller

A marshaller is an object that converts a value from Json-Logic-Typed
(leaf node in syntax tree) into a Scala data type.

A marshaller is defined by the trait `Marshaller` and must implement the method:

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

### Build a custom serializer

A `Serializer` converts a Scala data structure `JsonLogicScala` into a json in Json-Logic-Typed format.

A `Serializer` parses a json following the Json-Logic-Typed format and to every found Type
relies on its associated `Marshaller`.

The association between Type and `Marshaller` is provided in the `SerializerConf` object.

## The unmarshaller and deserializer


Json Logic Scala comes a built-in `Deserializer` that handles a number of
[pre-defined Types]({% link _posts/concept-of-types.md %}).

You may want to parse more Types than those predefined by the library. For example,
you may want to parse a custom class that you have implemented.

### What is an Unmarshaller ?

An unmarshaller is an object that converts a value from Json-Logic-Typed JSON
(i.e. a leaf node in syntax tree) to a Scala data structure `JsonLogicCore`.

An unmarshaller is defined by the trait `Unmarshaller` and must implement the
method

```scala
def unmarshal(jsValue: JsValue): Any
```

Each Type is associated with its own `Unmarshaller`.

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

### What is a Deserializer ?

A `Deserializer` parses JSON in Json-Logic-Typed format into a Scala data structure `JsonLogicScala`.

A `Deserializer` follows the Json-Logic-Typed format to parse JSON.
For every found Type, the operation relies on its associated `Unmarshaller`.

The association between Type and `Unmarshaller` is provided in the `DeserializerConf` object.
