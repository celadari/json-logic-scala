package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.{ComposeLogic, Decoder, JsonLogicCore}
import play.api.libs.json._



/** The base object for JsonLogicScala */
case object JsonLogicScala {

  object Person {
    implicit val personRead: Reads[Person] = Json.reads[Person]
  }

  case class Person(name: String, kids: Int)

  def main(args: Array[String]): Unit = {
    val js = """[{"<": [{"var": "car", "type": "int"}, {"var": "voiture", "type": "long"}, {"var": "jack", "type": "person"}]}, {"jack": {"name": "Jack", "kids": 5}, "car": 2, "voiture": 5}]"""

    implicit val myDecoder: Decoder = new Decoder {
      override def customDecode(json: JsValue, otherType: String)(implicit reads: Array[Reads[_]] = Array()): Any =
        otherType match {
          case "person" => json.as[Person]
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
    }
    val json = Json.parse(js).as[JsonLogicCore]
    println(json.asInstanceOf[ComposeLogic].conditions.mkString("[", ", ", "]"))
  }

}