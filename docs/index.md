---
layout: default
title: Home
nav_order: 0
description: "Build complex rules, serialize them as JSON, and execute them in Scala."
permalink: /
---
{% include mathjax.html %}

# Json Logic Scala
{: .fs-9 }

Build complex rules, serialize them as JSON, and execute them in Scala.
{: .fs-6 .fw-300 }

[Get started now](#what-is-json-logic){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 } [View it on GitHub](https://github.com/celadari/json-logic-scala){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## What is Json Logic ?
[Json Logic](https://jsonlogic.com/) is a recursive json format to represent expressions (binary, mathematical, linguistic, ...). Keys represent operator's codename and
values representing operands/sub-expression.

Json logic enables separating logic and evaluation, which can be delegated to another machine.
It also gives possibility to separates logic and data (example below).


#### Example, the following expression:

$$\mathbf{price} \ge 20\ \&\ \mathbf{label}\neq\mathbf{label2}$$

can be translated in json-logic format:

```json
[{
  "and": [
    {">=": [
      {"var":  "price"},
      20
    ]},
    {"!=": [
      {"var":  "label"},
      "label2"
    ]}
  ]
},
  {
    "price": ...,
    "label": ...
  }
]
```


## What is Json Logic Typed ?

**Json Logic Typed is a json logic format which annotates values' data-types.** Unlike simple json logic which is only usable
in dynamic languages, json logic typed can be applied to **static type languages - such as Scala.**

Json Logic Typed also enforces you to separate data and logic.

#### Examples

* The following json is a **valid json-logic** example and **a non-valid json-logic-typed** example.
```json
[{
    "and": [
        {">=": [
            {"var":  "price"},
            20
        ]},
        {"!=": [
            {"var":  "label"},
            "label2"
        ]}
    ]
},
{
    "price": ...,
    "label": ...
}]
```

* The following json is **a valid json-logic-typed** example (thus a **valid json-logic** example).
```json
[{
    "and": [
        {">=": [
            {"var":  "price_var"},
            {"var":  "price_value", "type":  {"codename":  "int"}}
        ]},
        {"!=": [
            {"var":  "label_var"},
            {"var":  "label_value", "type":  {"codename":  "int"}}
        ]}
    ]
},
{
    "price_var": ...,
    "price_value": ...,
    "label_var": ...,
    "label_value": ...
}]
```




