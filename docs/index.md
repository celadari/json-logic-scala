---
layout: default
title: Home
nav_order: 1
description: "Build complex rules, serialize them as JSON, and execute them in Scala."
permalink: /
---
<script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/latest.js?config=TeX-MML-AM_CHTML' async></script>

# Json Logic Scala
{: .fs-9 }

Build complex rules, serialize them as JSON, and execute them in Scala.
{: .fs-6 .fw-300 }

[Get started now](#what-is-json-logic){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 } [View it on GitHub](https://github.com/celadari/json-logic-scala){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## What is Json Logic ?
[Json Logic](https://jsonlogic.com/) is a json format to represent expressions (binary, mathematical, linguistic, ...). It is a recursive data-format consisting of keys represent operator's codename and
values representing operands/sub-expression.

Json logic enables separating logic and evaluation, which can be delegated to another component.

Example, the following expression:
$$\mathbf{price} \ge 20 \and \mathbf{label}\neq\mathbf{label2}$$


```json
{
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
}
```


## What is Json Logic Extended ?
