---
title: "Concept of Type for Parsing/Unparsing"
author: Charles
nav_order: 3
category: Jekyll
layout: post
---

## Behind the concept of "Type" in Json-Logic-Typed

As a reminder Json Logic Typed is a json logic format which annotates value data-type.

The whole concept of Json-Logic-Typed is to annotate type information about values in Json-Logic format.
Providing type information enables choosing the right data structure at parsing time when dealing
with static-typed languages such as Scala.

As an example:
```json
{
  "value1": 45
}
```
the following JSON can be easily parsed in a dynamically-typed language - python/perl/javascript - as
as implementation doesn't need to know in advance the type behind key "value1".

When dealing with a static-typed language such as Scala, implementation needs to know in advance
the types in json fields.
Json-logic-Scala, only accepts Json-Logic-Typed, and addresses this drag by defining the Scala data structure
to be used to parse a json field/value according to its annotated Type.

## How to annotate Type in Json-Logic-Typed format
Type is annotated after field "type" in a "var" operator json (leaf node in corresponding syntax tree).

### Simple Type
A simple type is simply defined by its "codename" field value.
```json
[{
    "...": [
            {"var":  "price_value", "type":  {"codename":  "int"}}
    ]
},
{
    "price_value": ...
}]
```

### Higher Type (option|array|map)
A higher type is a composition of simple and/or higher types.
A higher Type represents generic types in Scala such as: Array, Option, Map.

It is recursively defined by its "codename" field value and its "paramType" field value.

Example 1:
In the following example, variable "price_values" is to be parsed as an `Array[Int]`
```json
[{
    "...": [
            {"var":  "price_values", "type":  {"codename":  "array", "paramType": {"codename":  "int"}}}
    ]
},
{
    "price_values": ...
}]
```

Example 2:
Higher Types can be composed.
In the following example, variable "category_to_price_values" is to be parsed as an `Map[String, Array[Int]]`
```json
[{
    "...": [
            {"var":  "category_to_price_values", "type":  {"codename":  "map", "paramType": {"codename":  "array", "paramType": {"codename":  "int"}}}}
    ]
},
{
    "category_to_price_values": ...
}]
```

## Default types in Json Logic Scala
Json Logic Scala comes with a number of built-in defined Types to avoid you to reinvent the wheel.

| Type  | Scala data structure  | is Higher Type    |
|---|---|---|
|   "boolean"   |    `Boolean`   |  no  |
|   "int"       |    `Int`       |  no  |
|   "long"      |    `Long`      |  no  |
|   "float"     |    `Float`     |  no  |
|   "double"    |    `Double`    |  no  |
|   "string"    |    `String`    |  no  |
|   "short"     |    `Short`     |  no  |
|   "byte"      |    `Byte`      |  no  |
|   "null"      |    `Null`      |  no  |
|   "option"     |    `Option`     |  yes  |
|   "array"      |    `Array`      |  yes  |
|   "map"      |    `Map`      |  yes  |

Higher Types require "paramType" field to be defined in json.

**This means that if you stick to those codenames for Type annotation in your json, you can simply use
default `Serializer`/`Deserializer` to parse/unparse your json.**
