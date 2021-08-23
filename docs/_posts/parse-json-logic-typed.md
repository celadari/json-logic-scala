---
title: Parse json to scala data structure
author: Charles
nav_order: 3
category: Jekyll
layout: post
parent: Getting started
---

Converting a json-logic-typed data to a scala data structure is a deserialize operation performed by
utility class `Deserializer`.

* A `Deserializer` is a class responsible for parsing json data and convert it to a scala structure.
Configuration is provided with a `DeserializerConf` object, if no object is provided the default one is used
instead.
* A `DeserializerConf` provides mapping from type in json-logic-typed to scala data structure.

### Parsing examples:

* With default `Deserializer` and `DeserializerConf`

```scala
import play.api.libs.json.Json

val jsonString: String = ...
val jsonLogicCore = Json.parse(jsonString).as[JsonLogicCore]
```

* With custom `DeserializerConf`

```scala
import play.api.libs.json.Json

val jsonString: String = ...
implicit val deserializerConf = DeserializerConf.createConf(...)
implicit val deserializer = new Deserializer()
val jsonLogicCore = deserializer.deserialize(jsonString)
```

* With custom `Deserializer`

```scala
import play.api.libs.json.Json

val jsonString: String = ...
implicit val deserializer = new Deserializer() {
  ...
  override def deserialize(jsonLogic: JsObject, jsonLogicData: JsObject): JsonLogicCore = {...}
}
val jsonLogicCore = deserializer.deserialize(jsonString)
```