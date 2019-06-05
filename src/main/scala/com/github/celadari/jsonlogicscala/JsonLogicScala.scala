package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.{Decoder, Encoder, JsonLogicCore, ValueLogic}
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

    implicit def personWrite[T](implicit fmt: Writes[T]): Writes[Person[T]] = new Writes[Person[T]] {
      override def writes(person: Person[T]): JsValue = {
        JsObject(Map("name" -> Json.toJson(person.name), "kids" -> Json.toJson(person.kids)))
      }
    }
  }

  case class Person[T](name: String, kids: T)
//
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

    implicit val myEncoder: Encoder = new Encoder {
      override def customEncode(json: ValueLogic[_], otherType: Any)(implicit writes: Array[Writes[_]]): (String, JsValue) = {
        otherType match {
          case person: Person[Int] => ("person[Int]", Json.toJson(person))
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
      }
    }

    val js2 = """[{"<": [{"var": "voiture", "type": "int"}, {"var": "avion", "type": "int"}]}, {"voiture": 2, "avion": 5}]"""
    val json2 = Json.parse(js2).as[JsonLogicCore]

    println(js2)
    println(Json.prettyPrint(Json.toJson(json2)))
//    println(json2.asInstanceOf[ComposeLogic].conditions.mkString("[", ", ", "]"))
//    println(json2.reduce)
//    println(json2)
//    json2.evaluate()
  }

}