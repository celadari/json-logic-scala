---
title: Parse JSON to a Scala data structure
author: Charles
nav_order: 4
category: Jekyll
layout: post
parent: Getting started
---

The `Deserializer` utility class converts JsonLogic-Typed data
into Scala data structure.

Configure the `Deserializer` class with the `DeserializerConf` object.
This object defines how types in JsonLogic-Typed map to a Scala data structure.
If no custom object is provided, the default `DeserializerConf` is used .

### Parsing examples:

* With default `Deserializer` and `DeserializerConf`

```scala
import play.api.libs.json.Json

val jsonString: String = ...
val jsonLogicCore = Json.parse(jsonString).as[JsonLogicCore]
```

* With a custom `DeserializerConf`

```scala
import play.api.libs.json.Json

val jsonString: String = ...
implicit val deserializerConf = DeserializerConf.createConf(...)
implicit val deserializer = new Deserializer()
val jsonLogicCore = deserializer.deserialize(jsonString)
```

* You can also customize `Deserializer` to your own case

```scala
import play.api.libs.json.Json

val jsonString: String = ...
implicit val deserializer = new Deserializer() {
  ...
  override def deserialize(jsonLogic: JsObject, jsonLogicData: JsObject): JsonLogicCore = {...}
}
val jsonLogicCore = deserializer.deserialize(jsonString)
```
