package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.{ComposeLogic, Decoder, JsonLogicCore}
import play.api.libs.json._



/** The base object for JsonLogicScala */
case object JsonLogicScala {

  object Person {
    implicit def personRead[T](implicit fmt: Reads[T]): Reads[Person[T]] = new Reads[Person[T]] {
      override def reads(json: JsValue): JsResult[Person[T]] = {
        val name = (json \ "name").as[String]
        val kids = (json \ "kids").as[T]
        JsSuccess(Person(name, kids))
      }
    }
  }

  case class Person[T](name: String, kids: T)

  def main(args: Array[String]): Unit = {
    val js = """[{"<": [{"var": "car", "type": "int"}, {"var": "voiture", "type": "long"}, {"var": "jack", "type": "person[Int]"}]}, {"jack": {"name": "Jack", "kids": 5}, "car": 2, "voiture": 5}]"""

    implicit val myDecoder: Decoder = new Decoder {
      override def customDecode(json: JsValue, otherType: String)(implicit reads: Array[Reads[_]] = Array()): Any =
        otherType match {
          case "person[Int]" => json.as[Person[Int]]
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
    }
    val json = Json.parse(js).as[JsonLogicCore]
    println(json.asInstanceOf[ComposeLogic].conditions.mkString("[", ", ", "]"))
  }

}