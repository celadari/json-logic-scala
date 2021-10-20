---
title: Serialize Scala data structure to JSON
author: Charles
nav_order: 420
category: Jekyll
layout: post
parent: "Parse, Serialize, Evaluate"
---

The `Serializer` utility class converts a Scala data structure to JsonLogic-Typed
data.

Configure the`Serializer` class with the `SerializerConf` object.
This object defines how Scala data structures map to the JsonLogic-Typed format.
If no custom object is provided, the default `SerializerConf` is used .

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
