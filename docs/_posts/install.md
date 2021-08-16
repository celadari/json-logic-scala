---
title: Install
author: Charles
nav_order: 1
category: Jekyll
layout: post
parent: Getting started
---

## Sbt

```scala
libraryDependencies += "org.celadari" %% "json-logic-scala" % "2.0"
```

## Gradle
```gradle
compile group: 'org.celadari', name: 'json-logic-scala_2.13', version: '2.0'
```

## Maven
```xml
<dependency>
  <groupId>org.celadari</groupId>
  <artifactId>play-json_2.13</artifactId>
  <version>2.0</version>
</dependency>
```
*Note: Sbt automatically manages scala versions. Desired scala version must be specified if using Gradle or Maven.*