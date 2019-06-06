package com.github.celadari.jsonlogicscala

import com.github.celadari.jsonlogicscala.core.{Decoder, Encoder, JsonLogicCore, ValueLogic}
import com.github.celadari.jsonlogicscala.refs.{RefComposeLogic, RefValueLogic}
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

    implicit val myDecoder: Decoder = new Decoder {
      override def customDecode(json: JsValue, otherType: String)(implicit reads: Array[Reads[_]] = Array()): Any =
        otherType match {
          case "person[Int]" => json.as[Person[Int]]
          case "person[Long]" => json.as[Person[Long]]
          case "person[Array[Int]]" => json.as[Person[Array[Int]]]
          case "person[Array[Array[Int]]]" => json.as[Person[Array[Array[Int]]]]
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
    }

    implicit val myEncoder: Encoder = new Encoder {
      override def customEncode(valueLogic: ValueLogic[_], otherType: String)(implicit writes: Array[Writes[_]]): JsValue = {
        val json = valueLogic.valueOpt.get
        otherType match {
          case "person[Int]" => Json.toJson(json.asInstanceOf[Person[Int]])
          case "person[Long]" => Json.toJson(json.asInstanceOf[Person[Array[Int]]])
          case "person[Array[Int]]" => Json.toJson(json.asInstanceOf[Person[Array[Int]]])
          case "person[Array[Array[Int]]]" => Json.toJson(json.asInstanceOf[Person[Array[Array[Int]]]])
          case _ => throw new IllegalArgumentException("Wrong argument.")
        }
      }
    }

//    val js = """[{"<": [{"var": "car", "type": "int"}, {"var": "voiture", "type": "long"}, {"var": "jack", "type": "person[Array[Array[Int]]]"}]}, {"jack": {"name": "Jack", "kids": [[5], [6]]}, "car": 2, "voiture": 5}]"""
//    val json = Json.parse(js).as[JsonLogicCore]
//    println(js)
//    println(Json.stringify(Json.toJson(json)))
//
//    val js2 = """[{"<": [{"var": "voiture", "type": "int"}, {"var": "avion", "type": "int"}]}, {"voiture": 2, "avion": 5}]"""
//    val json2 = Json.parse(js2).as[JsonLogicCore]
//
//
//    val js3 = """[true, {}]"""
//    println((Json.parse(js3).as[JsonLogicCore].reduce))

//    val js4 = """[{">": [{"var": 0, "type": "int"}, {"var": 1, "type": "int"}]}  , [6, 4]]"""
//    val json4 = Json.parse(js4).as[JsonLogicCore]
//    println(json4.reduce)
//    println(Json.stringify(Json.toJson(json4)))

    //    println(json2.asInstanceOf[ComposeLogic].conditions.mkString("[", ", ", "]"))
//    println(json2.reduce)
//    println(json2)
//    json2.evaluate()

    val ref = RefComposeLogic[String]("AND", Array(
      RefComposeLogic[String]("=", Array(RefValueLogic("voiture", "int"), RefValueLogic("camion", "int"))),
      RefComposeLogic[String]("<=", Array(RefValueLogic("avion", "int"), RefValueLogic("sous-marin", "int")))
    ))

    val vals = Map("voiture" -> 54, "camion" -> 54, "avion" -> 100, "sous-marin" -> 154)

    val jsonLogicCore = JsonLogicCore.fromRefs(ref, vals)

    println(Json.prettyPrint(Json.toJson(jsonLogicCore)))
  }

}