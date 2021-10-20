---
title: Example workflow 
author: Charles
nav_order: 220
category: Jekyll
layout: post
parent: Quick start
---

{% include mathjax.html %}

# An end-to-end example of Json Logic Scala

This example starts from typed JsonLogic data, then deserializes it
into a Scala data structure. With json-logic-scala, it would be just
as easy to perform the reverse process (starting from a Scala struture and
serializing it as Typed JsonLogic).

### 1. Represent an expression as JsonLogic-typed

Consider the following expression.

$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$


You can represent this expression, _along with possible values_ in a single Json data file:

```json
 [
  {
    "and": [
      {
        ">=": [
          {
            "var": "price",
            "type": {
              "codename": "int"
            }
          },
          {
            "var": "price_threshold",
            "type": {
              "codename": "int"
            }
          }
        ]
      },
      {
        "!=": [
          {
            "var": "value",
            "type": {
              "codename": "string"
            }
          },
          {
            "var": "value_to_be_different",
            "type": {
              "codename": "string"
            }
          }
        ]
      }
    ]
  },
  {
    "price": 45,
    "price_threshold": 20,
    "value": "label456",
    "value_to_be_different": "label2"
  }
]
 ```

Note that, in this snippet, data and logic are separate objects.

### 2. Deserialize the Json as a Scala Data structure

Using Json Logic Scala, you can deserialize the preceding JSON snippet as a
scala data structure. The expression and data looks like this:

```scala
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.{INT_CODENAME, STRING_CODENAME}

val tree = new ComposeLogic("and", Array(
  new ComposeLogic(">=", Array(
    ValueLogic(Some(45), typeOpt = Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("price")),
    ValueLogic(Some(20), typeOpt = Some(SimpleTypeValue(INT_CODENAME)), pathNameOpt = Some("price_threshold"))
  )),
  new ComposeLogic("!=", Array(
    ValueLogic(Some("label456"), typeOpt = Some(SimpleTypeValue(STRING_CODENAME)), pathNameOpt = Some("value")),
    ValueLogic(Some("label2"), typeOpt = Some(SimpleTypeValue(STRING_CODENAME)), pathNameOpt = Some("value_to_be_different"))
  ))
))
```

### 3. Evaluate the expression

Finally, you can evaluate this Scala data structure.

```
val evaluator = new EvaluatorLogic
evaluator.eval(tree)
``` 

The price value is 45, and the tested threshold is 20.
45 is greater than 20, so the result will be `false`.

