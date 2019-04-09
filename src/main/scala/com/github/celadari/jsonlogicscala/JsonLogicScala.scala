package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.ComposeLogic
import com.github.celadari.jsonlogicscala.core.JsonLogicCore
import play.api.libs.json._



/** The base object for JsonLogicScala */
case object JsonLogicScala {

  def main(args: Array[String]): Unit = {
    val js = """[{"<": [{"var": "car"}, {"var": "voiture"}]}, {"car": 2, "voiture": 5}]"""
    val json = Json.parse(js).as[ComposeLogic[Int]]
    println(json.conditions.mkString("[", ", ", "]"))
  }

}