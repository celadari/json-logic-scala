---
title: Serialize scala data structure to json
author: Charles
nav_order: 4
category: Jekyll
layout: post
parent: Getting started
---

Converting a scala data structure to a json-logic-typed data is a serialize operation performed by
utility class `Serializer`.

* A `Serializer` is a class responsible for converting a scala structure to json.
Configuration is provided with a `SerializerConf` object, if no object is provided the default one is used
instead.
* A `SerializerConf` provides mapping from scala data structure to type information in json-logic-typed format.



### Serializing examples:

* With default `Serializer` and `SerializerConf`

```scala
import play.api.libs.json.Json

val jsonLogicCore: JsonLogicCore = ...
val jsonString = Json.stringify(Json.toJson(jsonLogicCore))
```

* With custom `SerializerConf`

```scala
import play.api.libs.json.Json

val jsonLogicCore: JsonLogicCore = ...
implicit val serializerConf = SerializerConf.createConf(...)
implicit val serializer = new Serializer()
val jsonString = serializer.serialize(jsonLogicCore)
```

* With custom `Serializer`

```scala
import play.api.libs.json.Json

val jsonLogicCore: JsonLogicCore = ...
implicit val serializer = new Serializer() {
  ...
  override def serialize(jsonLogic: JsonLogicCore): (JsValue, JsValue) = {...}
}
val jsonString = serializer.serialize(jsonLogicCore)
```
