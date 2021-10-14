---
title: "Concept of Type for Parsing/Unparsing"
author: Charles
nav_order: 3
category: Jekyll
layout: post
---

# Behind the concept of "Type" in Json-Logic-Typed

Json Logic Typed is a variant of the jsonLogic format that annotates data-type
values.

When using statically typed languages like Scala,
providing type information lets an application choose the right data structure
at parsing time.

Consider the following example:

```json
{
  "value1": 45
}
```

This JSON snippet can be easily parsed in a dynamically-typed language like
Python, Perl, or Javascript.
In these languages, the implementation does not need a predefined type
for the key `value1`.

On the other hand, in typed languages like Scala, the implementation
needs to know the values' types in advance.

Json-logic-Scala accepts only JSON in Json-Logic-Typed format.
To address this, json-logic-scala defines a Scala data structure that you
can use to parse a JSON field or value according to its annotated Type.

## How to annotate type in Json-Logic-Typed format

Type is annotated after the field `"type"` in a `"var"` operator JSON
(i.e. the leaf node in corresponding syntax tree).

### Simple Type

A simple type is simply defined by its `codename` field value.

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
A higher type represents generic types in Scala like arrays, options, and maps.

It is recursively defined by its `codename` field value and its `paramType` field value.

**Example 1**:

In the following example, the variable `price_values` is to be parsed as an `Array[Int]`

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

**Example 2**:

Higher Types can be composed.
In the following example, the variable `category_to_price_values` is parsed as
an `Map[String, Array[Int]]`

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
So that you don't have to reinvent the wheel, Json Logic Scala comes with a
number of built-in defined Types.

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

Higher Types require `paramType` field to be defined in json.

**Key takeaway**: If you stick to those codenames for Type annotation in your JSON,
you can simply use the default `Serializer` and `Deserializer` to parse/unparse your
JSON.
