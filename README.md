# Json Logic Scala
Build complex rules, serialize them as JSON, and execute them in Scala
Json-logic-scala enables you to serialize in JSON format logical expressions.
It also enables you to load a scala object from a logical expression/JSON.

Due to Scala's strong static typed language nature, json-logic-scala requires JSON to add tell type in json.

### Why would you use json-logic-scala ?
The [JsonLogic format](http://jsonlogic.com/) is designed to allow you to share rules (logic) between
front-end and back-end code (regardless of language difference), even to store
logic along with a record in a database.

Logic that has been exported from another language can be applied quickly on
scala.

### Scala Versions

This project is compiled, tested, and published for the following Scala versions:
1. 2.10.3
2. 2.11.12
3. 2.12.6

## 1. Installation

To get started, add json-logic-scala as a dependency to your project:

* sbt
    ```sbt
    libraryDependencies += "com.github.celadari" %% "json-logic-scala" % "latest.integration"
    ```

* Gradle
    ```gradle
    compile group: 'com.github.celadari', name: 'json-logic-scala_2.12', version: 'latest.integration'
    ```
* Maven
    ```maven
    <dependency>
      <groupId>com.github.celadari</groupId>
      <artifactId>play-json_2.12</artifactId>
      <version>latest.integration</version>
    </dependency>
    ```
Json-logic-scala supports Scala 2.11 and 2.12. Choosing the right JAR is automatically managed in sbt. If you're using Gradle or Maven then you need to use the correct version in the artifactId.

## 2. Main concepts: Boolean-Algebra-Tree
Boolean expressions are complex boolean statements composed of atoms, unary, binary and multiple operators.
Atoms are assigned a value, and can be fed to a binary or unary expression.
For example, the logical expression
$price \gte 20 \and label \neq label2$ can be parsed to the following Abstract Syntax Tree:

A tree representation of the logical expression is very convenient. After isolating the outermost operator of the
expression (the operator which is enclosed with the fewest amount of parentheses), the logical expression can be split on
said operator into different branches representing themselves logical expressions. These different expressions can be further
split into different branches until reaching leaves Node which represent single atoms. Evaluating the logical expression in
its tree representation is evaluated recursively. Each Internal Node needs to have its children nodes evaluated before
being evaluated. Leaf Nodes represent variables/values.


## 3. Example
Let's suppose you have a parquet/csv file on disk and you want to remember/transfer
filtering rules before loading it.

| price (€) | quantity | label    | label2    | clientID | date                |
|-----------|----------|----------|----------|----------|---------------------|
| 54        | 2        | t-shirts | t-shirts | 245698   | 2018-01-12 09:12:00 |
| 68        | 1        | pants    | shoes    | 478965   | 2019-07-24 15:24:00 |
| 10        | 2        | sockets  | hat      | 478963   | 2020-02-14 16:22:00 |
|...........|..........|..........|..........|..........|.....................|

Let's suppose we are only interested in rows which satisfy logical expression:
$price \gte 20 \and label \neq label2$.
If you want to store the logic (logical expression) in an universal format that can
be shared between scala, R, python code you can store in jsonLogic format.

For the logic:
```json
{
  "and": [{
            "<=": [
                    {"var": "colA", "type": "column"},
                    {"var": "valA", "type": "value"}
                  ]
          },
          {
            "!=": [
                    {"var": "colB", "type": "column"},
                    {"var": "colC", "type": "column"}
                  ]
          }
         ]
}
```
For the values:
```json
{
    "colA": {"name": "price (€)"},
    "valA": {"value": 20, "type": "int"},
    "colB": {"name": "label"},
    "colC": {"name": "label2"}
}
```

## 4. Usage



### 4.1 Importing/Exporting
A boolean decision tree is represented by `JsonLogicCore` class - which has two subtypes: `ComposeLogic` and `ValueLogic`.

* An Internal Node (i.e. conditional node) is of type: `ComposeLogic`.
* A Leaf Node (i.e. value/variable node) is of type: `ValueLogic`.

To use **Json Logic Scala**, you should start by defining or importing a `JsonLogicCore` instance (we'll see how to evaluate it latter below).

#### Define
You should define how to read/write Leaves Nodes using [Play](https://www.playframework.com/documentation/latest/ScalaJson) library.
There can be severa


## Scaladoc API

The Scaladoc API for this project can be found [here](http://celadari.github.io/json-logic-scala/latest/api).

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
