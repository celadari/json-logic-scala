package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.JsonLogicCore
import play.api.libs.json._



/** The base object for JsonLogicScala */
case object JsonLogicScala {

  def main(args: Array[String]): Unit = {
    val js = """{"<": [{"var": 2}, {"var": 3}]}"""
    println(Json.parse(js).as[JsonLogicCore[Int]])
  }

}