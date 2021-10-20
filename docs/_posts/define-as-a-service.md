---
title: "Advanced: Define Custom objects as Java Services"
author: Charles
nav_order: 650
category: Jekyll
layout: post
parent: "Customize classes"
---

# Define custom objects as Java services

## What is to be implemented as a Service ?

Other pages demonstrate how to manually add
`Marshaller`/`Unmarshaller`/`MethodConf`/`EvaluatorValueLogic` to configuration.

Custom (and default) implementations of those interfaces can be seen as providers for them.
Implementations can be installed in the form of extensions.

To pass implementations as service providers, place provider-configuration files
under a directory in the resource directory `META-INF/services`. The directory's name is the
fully qualified binary name of the interface/service's type.

The directory contains a list of files. Each file represents an implementation
of the interface, with a fully qualified binary name, and with parameters of its
own.

## File parameters

Each file contains a number of parameters to define an implementation/service provider.

There are several possible parameters:
* `className`: binary fully qualified name of current implementation.
* `singleton`: boolean to indicate if Scala `object`, otherwise Scala `class`.
* `codename`: Type codename that this implementation should be associated with. See [Types]({% link _posts/concept-of-types.md %})
* `constructorArgNames`: list of constructor argument names in single a `String`,
separated by `sep`.
* `sep`: string separator in `constructorArgNames`.

**You must also provide Constructor arguments in the file in the form
`argumentName=argumentValue`**.

## How to declare a `Marshaller` as a service

Just add a directory named `com.celadari.jsonlogicscala.serialize.Marshaller`
in your `META-INF/services/` directory.

```
project
│   ...
│   build.sbt
│
└───META-INF
│   │   ...
│   │   ...
│   │
│   └───services
│       │   file111.txt
│       │   file112.txt
│       │   ...
│       └───com.celadari.jsonlogicscala.serialize.Marshaller
│           │   codenameCustomClassA
│           │   codenameCustomClassB
│           │   ...
└
```


**Example**

You may implement the following class

```scala
// package com.myownpackage.definitions
class A(...)

// package com.myownpackage.marshallers
class MarshallerClassA(param1: Int, param2: String) extends Marshaller {
  def marshal(value: Any): JsValue = {
    value match {
      case a: A => ...
      case _ => throw ...
    }
  }
}
```

Instead of passing it manually to `Serializer.createConf`, you can just define
file `classA` in the directory `META-INF/services/com.celadari.jsonlogicscala.serialize.Marshaller/`

```properties
className=com.myownpackage.marshallers.MarshallerClassA
singleton=false
param1=45
param2="My own parameter"
constructorArgNames=param1;param2
sep=;
codename=classA
```

## How to declare a `Unmarshaller` as a service ?

Just add a directory named `com.celadari.jsonlogicscala.deserialize.Unmarshaller`
in your `META-INF/services/` directory.

```
project
│   ...
│   build.sbt
│
└───META-INF
│   │   ...
│   │   ...
│   │
│   └───services
│       │   file111.txt
│       │   file112.txt
│       │   ...
│       └───com.celadari.jsonlogicscala.deserialize.Unmarshaller
│           │   codenameCustomClassA
│           │   codenameCustomClassB
│           │   ...
└
```

**Example**

You can implement the following class:

```scala
// package com.myownpackage.definitions
class A(...)

// package com.myownpackage.unmarshallers
class UnmarshallerClassA(param1: Int, param2: String) extends Unmarshaller {
  def unmarshal(jsValue: JsValue): Any = {
    val constructorArg1 = (jsValue \ ...).as[...]
    ...
    val constuctorArgN = (jsValue \ ...).as[...]
    new A(constuctorArg1, ..., constuctorArgN)
  }
}
```

Instead of passing it manually to `Deserializer.createConf`, you can just define
the file `classA` in the directory `META-INF/services/com.celadari.jsonlogicscala.deserialize.Unmarshaller/`

```properties
className=com.myownpackage.unmarshallers.UnmarshallerClassA
singleton=false
param1=45
param2="My own parameter"
constructorArgNames=param1;param2
sep=;
codename=classA
```


## How to declare an `EvaluatorValueLogic` as a service

Just add a directory named `com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic`
in your `META-INF/services/` directory.

```
project
│   ...
│   build.sbt
│
└───META-INF
│   │   ...
│   │   ...
│   │
│   └───services
│       │   file111.txt
│       │   file112.txt
│       │   ...
│       └───com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic
│           │   codenameCustomClassA
│           │   codenameCustomClassB
│           │   ...
└
```

**Example**

You can implement the following classes, replacing objects `A` with objects `B` before evaluation.

```scala
// package com.myownpackage.definitions
class A(...)
class B(...)

// package com.myownpackage.evaluators
class EvaluatorValueLogicClassA(param1: Int, param2: String) extends EvaluatorValueLogic {
  def evaluateValueLogic(value: Any): Any = {
    new B(...)
  }
}
```

Instead of passing it manually to `EvaluatorLogicConf.createConf`, you can just
the define file `classA` in the directory `META-INF/services/com.celadari.jsonlogicscala.evaluate.EvaluatorValueLogic/`

```properties
className=com.myownpackage.evaluators.EvaluatorValueLogicClassA
singleton=false
param1=45
param2="My own parameter"
constructorArgNames=param1;param2
sep=;
codename=classA
```

## How to declare `MethodConf` as a service

Just add a directory named `com.celadari.jsonlogicscala.evaluate.MethodConf`
in your `META-INF/services/` directory.

```
project
│   ...
│   build.sbt
│
└───META-INF
│   │   ...
│   │   ...
│   │
│   └───services
│       │   file111.txt
│       │   file112.txt
│       │   ...
│       └───com.celadari.jsonlogicscala.evaluate.MethodConf
│           │   codenameCustomOperatorA
│           │   codenameCustomOperatorB
│           │   ...
└
```

**Example**:

You can implement following classes, replacing objects `A` with objects `B` before evaluation.
```scala

// package com.myownpackage.operators
object OperatorA extends Operator {
  ...
}
```

Instead of passing it manually to `EvaluatorLogicConf.createConf`, you can just define
file `classA` in the directory `META-INF/services/com.celadari.jsonlogicscala.evaluate.MethodConf/`

```properties
className=com.myownpackage.operators.OperatorA
singleton=true
codename=operator_a
```
