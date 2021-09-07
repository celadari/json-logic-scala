---
title: "Data structures: scala representation (part 2)"
author: Charles
nav_order: 3
category: Jekyll
layout: post
parent: Getting started
---
{% include mathjax.html %}

# Scala data structures: global view (part 2/2)

The main takeaway difference between json-logic format and json-logic-typed format is that json-logic-format
not only represents an expression but contains information about value types (Int, String, Boolean, ...).

### Purpose of `TypeValue`
**Json-logic-scala contains scala data structures to represent type information in json-logic-typed and utilities to make
conversion between data (json) and data representation (scala data structure).**

`SerializerConf` and `DeserializerConf` inform `Serializer` and `DeserializerConf` how to map `TypeValue`
to scala types. Thus enabling serialization and deserialization.


**Scala data structures to represent value types in json-logic-typed format are basic recursive structures.**

* A `TypeValue` object is a scala data structure representation of a value type in json-logic-typed format. Interface exposes one 
attribute `codename` of type String.

* A `SimpleTypeValue` - subtype of `TypeValue` - object is a scala data structure representation of a simple value (String, Int, Boolean)
type in json-logic-typed format. By simple, it means no generic types. Check subtypes of `TypeValue` below for generic types representing (Map, Array, Option).

* An `OptionTypeValue` - subtype of `TypeValue` - object is a scala data structure representation of an optional value (Option[String], Option[Int], ...)
in json-logic-typed format. It consists of two attributes: `codename` (set to `"option""`) and `paramType` of type `TypeValue`.

* An `MapTypeValue` - subtype of `TypeValue` - object is a scala data structure representation of a map - keys must be string type - (Map[String, String], Map[String, Int], ...)
in json-logic-typed format. It consists of two attributes: `codename` (set to `"map""`) and `paramType` of type `TypeValue`.

* An `ArrayTypeValue` - subtype of `TypeValue` - object is a scala data structure representation of an array (Array[String], Array[Int], ...)
in json-logic-typed format. It consists of two attributes: `codename` (set to `"array""`) and `paramType` of type `TypeValue`.

### Predefined generic types
*`OptionTypeValue`, `MapTypeValue`, `ArrayTypeValue`* each represents a generic scala type (respectively Option[], Map[], Array[]) and may be composed if necessary.

For example, the scala type `Array[Map[String, Option[Int]]]` can be represented by the following `TypeValue` structure `ArrayTypeValue(MapTypeValue(SimpleTypeValue(INT_CODENAME)))` where `INT_CODENAME` is a String codename.

### Purpose of generic types
**`OptionTypeValue`, `MapTypeValue`, `ArrayTypeValue` makes it much more convenient to work with Option, Map, Array instead of defining a new `SimpleType` - thus `SerializerConf` and `DeserializerConf` - to each sub-subtype (Array[Int], Array[String], ..., Option[Int], Option[String], ...)**

### Summary table

| `TypeValue` object  | Can be used to represent scala data structures  |
|---|---|
| `SimpleTypeValue`  |  `Int`, `String`, `Boolean` but also custom types you might implement yourself `MyOwnClassA`, `MyOwnClassB`, ... |
| `OptionTypeValue`  | `Option[Int]`, `Option[String]` but can also represent composed generic types `Option[Option[Int]]`, `Option[Array[Int]]` and even involve custom data types `Option[Array[MyOwnClassA]]`, ... |
| `MapTypeValue`  | `Map[String, Int]`, `Map[String, String]` but can also represent composed generic types `Map[String, Option[Int]]`, `Map[String, Array[Int]]` and even involve custom data types `Map[String, Array[MyOwnClassA]]`, ... |
| `ArrayTypeValue` | `Array[Int]`, `Array[String]` but can also represent composed generic types `Array[Option[Int]]`, `Array[Array[Int]]` and even involve custom data types `Array[Option[MyOwnClassA]]`, ... |