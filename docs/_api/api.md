---
title: API Documentation
author: Charles
nav_order: 10
category: Jekyll
layout: default
has_children: false
---
<style>
    .column {
        float: left;
        width: {{ 100.0 | divided_by: site.data.api_versions.size }}%;
    }

    /* Clear floats after the columns */
    .row:after {
        content: "";
        display: table;
        clear: both;
    }
</style>


<div class="row">
    {% for scala_version_apis in site.data.api_versions %}
        <div class="column">
            {% capture scala_version %}{{ scala_version_apis["scala-version"] }}{% endcapture %}
            scala-version: {{ scala_version }}
            <li><a href="scala-{{ scala_version }}/latest/api/index.html"> latest version </a></li>
            {% for api_version in scala_version_apis["json-logic-versions"] %}
                <li>
                    <a href="scala-{{ scala_version }}/{{ api_version }}/api/index.html"> version - {{ api_version }} </a>
                </li>
            {% endfor %}
        </div>
    {% endfor %}
</div>
