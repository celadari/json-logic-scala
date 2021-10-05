---
title: "Advanced: Define a Marshaller/Unmarshaller/Operator/EvaluatorValueLogic as a Java Service"
author: Charles
nav_order: 9
category: Jekyll
layout: post
---

# What is to be implemented as a Service ?

Other pages manually add `Marshaller`/`Unmarshaller`/`Operator`/`EvaluatorValueLogic` to
configuration.

All

Custom (and default) implementations of those interfaces can be seen as providers for them.
Implementations can be installed in the form of extensions.

Passing implementations as service providers is done by placing a provider-configuration
files under a directory in the resource directory META-INF/services. The directory's name is the
fully-qualified binary name of the interface/service's type.
The directory contains a list of files; each file representing an implementation of the
interface (whose fully-qualified binary name) with parameters of its very own.


# How to declare a `Serializer` as a service ?




