---
title: " JSON to Scala structures (and vice versa)"
author: Charles
nav_order: 3
category: Jekyll
layout: post
parent: Data representation
---
{% include mathjax.html %}

# Scala data structures: global view

In plain JsonLogic format, data represents only expressions and values.
With Json-Logic-typed, you can also include information about *value types*
(e.g. `Int`, `String`, `Boolean`).

When they represent value types in Json-Logic-typed, the Scala data structures
are basic recursive structures.

<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [The JsonLogicCore object represents typed JsonLogic](#the-jsonlogiccore-object-represents-typed-jsonlogic)
- [The `TypeValue` object represents a value type](#the-typevalue-object-represents-a-value-type)
    - [Predefined generic types](#predefined-generic-types)
    - [Reference of `TypeValue` objects](#reference-of-typevalue-objects)

<!-- markdown-toc end -->


## The JsonLogicCore object represents typed JsonLogic

`JsonLogicCore` is a Scala data structure that lets you represent
typed JsonLogic. The interface exposes one attribute: `operator`, which has a
type `String`.

The `JsonLogicCore` object has the following subtypes:

*  `ValueLogic`: a Scala data structure that represents a data-node in
^JsonLogic-Typed datum. It consists of several attributes, including:
   * `operator` of type String,
   * `typeCodenameOpt` of type Option[TypeValue]
 ([TypeValue is detailed in another section]({% link _posts/scala-data-structures-global-view.md %}))
   * `valueOpt` of generic-type Option[T].
* `ComposeLogic`: a Scala data structure that represents an operator-node in
^JsonLogic-Typed datum. It consists of two attributes:
    * `operator` of type String
    * `conditions` of type Array[JsonLogicCore]

<!--How about adding examples?-->

## The `TypeValue` object represents a value type

> &#128712;  The `Serializer` and `Deserializer` classes define how `TypeValue` maps to Scala types.
> For more information about how to use and configure these classes,
> see the page [Parse JSON to Scala](./parse-json-logic-typed).

 `TypeValue` is a Scala data structure that represents a value type in
 JsonLogic-Typed format. The interface exposes one  attribute:`codename`, of
type `String`.

`TypeValue` has a the following subtypes:

* `SimpleTypeValue`: a Scala data structure representation of a _simple value_
(`String`, `Int`, `Boolean`)
In this case, simple means no generic types.

* An `OptionTypeValue`: a Scala data structure representation of an optional value (Option[String], Option[Int], ...)
in JsonLogic-Typed format. It consists of two attributes:
    * `codename` (set to `"option""`)
    * `paramType` of type `TypeValue`.

* An `MapTypeValue`: a Scala data structure representation of a map .
Keys must be in JsonLogic-typed format, of type `String` ─ (Map[String, String], Map[String, Int], ...)
It consists of two attributes:
    * `codename` (set to `"map""`)
    * `paramType`, of type `TypeValue`.

* An `ArrayTypeValue`: a Scala data structure representation of an array (Array[String], Array[Int], ...)
in JsonLogic-Typed format. It consists of two attributes:
   * `codename` (set to `"array""`)
   * `paramType` of type `TypeValue`.

### Predefined generic types

*`OptionTypeValue`, `MapTypeValue`, and `ArrayTypeValue`* each represent a
generic Scala type (respectively, option[], map[], and array[]).

Instead of defining a new `simpletype` to each sub-subtype, these generic types
make it much more convenient to work with options, maps, and arrays.

They also can be composed if necessary.
For example, the Scala type `Array[Map[String, Option[Int]]]` can be represented
by the following `TypeValue` structure

```scala
ArrayTypeValue(MapTypeValue(SimpleTypeValue(INT_CODENAME)))
```

where `INT_CODENAME` is a string codename.

### Reference of `TypeValue` objects

| `TypeValue` object | Can be used to represent Scala data structures | And can also represent |
|--------------------|--------------------------------------------------------------------------------------------------------------|------------|
| `SimpleTypeValue`  | `Int`, `String`, `Boolean` | Custom types you might implement yourself `MyOwnClassA`, `MyOwnClassB`, ...    |
| `OptionTypeValue`  | `Option[Int]`, `Option[String]` | The composed generic types `Option[Option[Int]]`, `Option[Array[Int]]`, and even involve custom data types `Option[Array[MyOwnClassA]]`, ...|
| `MapTypeValue`     | `Map[String, Int]`, `Map[String, String]` |The composed generic types `Map[String, Option[Int]]`, `Map[String, Array[Int]]` and even involve custom data types `Map[String, Array[MyOwnClassA]]`, ... |
| `ArrayTypeValue`   | `Array[Int]`, `Array[String]` |the composed generic types `Array[Option[Int]]`, `Array[Array[Int]]` and even involve custom data types `Array[Option[MyOwnClassA]]`, ...|
