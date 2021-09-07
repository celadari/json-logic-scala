---
title: API Documentation
author: Charles
nav_order: 2
category: Jekyll
layout: default
has_children: false
---

{{ page.dir }}

<ul>
    {% for item in page.dir %}
        {% if item.dir == page.dir and item.path != page.path %}
           <li>  {{ item.name }} </li>
        {% endif %}
   {% endfor %}
</ul>