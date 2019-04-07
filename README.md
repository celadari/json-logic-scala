# Json Logic Scala
Build complex rules, serialize them as JSON, and execute them in Scala

## Problem Statement

This little project aims to solve the following problems:

1. Make stuff more awesome.
2. Remove the less awesome stuff from your project.

## Configuration

Add the Sonatype.org Releases repo as a resolver in your `build.sbt` or `Build.scala` as appropriate.

```scala
resolvers += "Sonatype.org Releases" at "https://oss.sonatype.org/content/repositories/releases/"
```

Add **Json Logic Scala** as a dependency in your `build.sbt` or `Build.scala` as appropriate.

```scala
libraryDependencies ++= Seq(
  // Other dependencies ...
  "com.github.celadari" %% "JsonLogicScala" % "0.0.1" % "compile"
)
```

## Scala Versions

This project is compiled, tested, and published for the following Scala versions:

1. 2.9.1
2. 2.9.1-1
3. 2.9.2
4. 2.9.3
5. 2.10.3
6. 2.11.12
7. 2.12.6


## Usage

To use **Json Logic Scala**, you should import it and call it...

## Scaladoc API

The Scaladoc API for this project can be found [here](http://celadari.github.io/json-logic-scala/latest/api).

## Examples

```scala
package org.example

import com.github.celadari.jsonlogicscala._

case object MyObject {
  // ...
}
```

## Wishlist

Below is a list of features we would like to one day include in this project

1. Support more awesome.
2. Decimate the not-awesome.

## License

*Json Logic Scala* is licensed under the MIT License.

    MIT License

    Copyright (c) 2019 celadari

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
