package com.github.celadari.jsonlogicscala.evaluate.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, MapTypeValue, SimpleTypeValue, TypeValue}


trait TestMap extends AnyFlatSpec with Matchers {

  val mapBool: Map[String, java.lang.Boolean] = Map("plane"-> true, "boat" -> false, "submarine" -> true, "blimp"-> true, "car" -> false)
  val mapInt: Map[String, java.lang.Integer] = Map("plane"-> 3, "boat" -> 56, "submarine" -> 78, "blimp"-> 53, "car" -> 0)
  val mapLong: Map[String, java.lang.Long] = Map("plane"-> 67L, "boat" -> 9L, "submarine" -> 672L, "blimp"-> 45L, "car" -> 4L)
  val mapDouble: Map[String, java.lang.Double] = Map("plane"-> 45d, "boat" -> 76d, "submarine" -> 90d, "blimp"-> 23d, "car" -> 1d)
  val mapFloat: Map[String, java.lang.Float] = Map("plane"-> 67f, "boat" -> 89f, "submarine" -> 56f, "blimp"-> 1f, "car" -> 0f)
  val mapString: Map[String, java.lang.String] = Map("plane"-> "I", "boat" -> "love", "submarine" -> "walking", "blimp"-> "in", "car" -> "New York")

  val mapArrBool: Map[String, Array[java.lang.Boolean]] = Map(
    "plane" -> Array(true, false, true),
    "boat" -> Array(true, true, true),
    "submarine" -> Array(false, false, true)
  )
  val mapArrInt: Map[String, Array[java.lang.Integer]] = Map("plane" -> Array(34, 56, 89), "boat" -> Array(2, 69, 82), "submarine" -> Array(83, 60, 4))
  val mapArrLong: Map[String, Array[java.lang.Long]] = Map("plane" -> Array(4L, 56L, 2L), "boat" -> Array(94L, 6L, 78L), "submarine" -> Array(31L, 79L, 45L))
  val mapArrDouble: Map[String, Array[java.lang.Double]] = Map(
    "plane" -> Array(67d, 49d, 9d),
    "boat" -> Array(3d, 23d, 87d),
    "submarine" -> Array(7d, 23d, 24d)
  )
  val mapArrFloat: Map[String, Array[java.lang.Float]] = Map(
    "plane" -> Array(84f, 37f, 61f),
    "boat" -> Array(22f, 8f, 75f),
    "submarine" -> Array(2f, 945f, 24f)
  )
  val mapArrString: Map[String, Array[java.lang.String]] = Map(
    "plane" -> Array("Charizard", "Dragonite", "Gyarados"),
    "boat" -> Array("Obelisk", "Ra", "Slifer"),
    "submarine" -> Array("Griezmann", "Pogba", "Mbappe")
  )

  val mapMapBool: Map[String, Map[String, java.lang.Boolean]] = Map(
    "airforce"-> Map("plane"-> true, "blimp"-> true),
    "navy" -> Map("boat" -> false, "submarine" -> true),
    "police" -> Map("car" -> false)
  )
  val mapMapInt: Map[String, Map[String, java.lang.Integer]] = Map(
    "airforce"-> Map("plane"-> 3, "blimp"-> 53),
    "navy" -> Map("boat" -> 56, "submarine" -> 78),
    "police" -> Map("car" -> 0)
  )
  val mapMapLong: Map[String, Map[String, java.lang.Long]] = Map(
    "airforce"-> Map("plane"-> 67L, "blimp"-> 45L),
    "navy" -> Map("boat" -> 9L, "submarine" -> 672L),
    "police" -> Map("car" -> 4L)
  )
  val mapMapDouble: Map[String, Map[String, java.lang.Double]] = Map(
    "airforce"-> Map("plane"-> 45d, "blimp"-> 23d),
    "navy" -> Map("boat" -> 76d, "submarine" -> 90d),
    "police" -> Map("car" -> 1d)
  )
  val mapMapFloat: Map[String, Map[String, java.lang.Float]] = Map(
    "airforce"-> Map("plane"-> 67f, "blimp"-> 1f),
    "navy" -> Map("boat" -> 89f, "submarine" -> 56f),
    "police" -> Map("car" -> 0f)
  )
  val mapMapString: Map[String, Map[String, java.lang.String]] = Map(
    "airforce"-> Map("plane"-> "I", "blimp"-> "in"),
    "navy" -> Map("boat" -> "love", "submarine" -> "walking"),
    "police" -> Map("car" -> "New York")
  )

  val mapBoolType: TypeValue = MapTypeValue(SimpleTypeValue(BOOL_CODENAME))
  val mapIntType: TypeValue = MapTypeValue(SimpleTypeValue(INT_CODENAME))
  val mapLongType: TypeValue = MapTypeValue(SimpleTypeValue(LONG_CODENAME))
  val mapDoubleType: TypeValue = MapTypeValue(SimpleTypeValue(DOUBLE_CODENAME))
  val mapFloatType: TypeValue = MapTypeValue(SimpleTypeValue(FLOAT_CODENAME))
  val mapStringType: TypeValue = MapTypeValue(SimpleTypeValue(STRING_CODENAME))

  val mapArrBoolType: TypeValue = MapTypeValue(ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(BOOL_CODENAME))))
  val mapArrIntType: TypeValue = MapTypeValue(ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(INT_CODENAME))))
  val mapArrLongType: TypeValue = MapTypeValue(ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(LONG_CODENAME))))
  val mapArrDoubleType: TypeValue = MapTypeValue(ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(DOUBLE_CODENAME))))
  val mapArrFloatType: TypeValue = MapTypeValue(ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(FLOAT_CODENAME))))
  val mapArrStringType: TypeValue = MapTypeValue(ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(STRING_CODENAME))))

  val mapMapBoolType: TypeValue = MapTypeValue(MapTypeValue(SimpleTypeValue(BOOL_CODENAME)))
  val mapMapIntType: TypeValue = MapTypeValue(MapTypeValue(SimpleTypeValue(INT_CODENAME)))
  val mapMapLongType: TypeValue = MapTypeValue(MapTypeValue(SimpleTypeValue(LONG_CODENAME)))
  val mapMapDoubleType: TypeValue = MapTypeValue(MapTypeValue(SimpleTypeValue(DOUBLE_CODENAME)))
  val mapMapFloatType: TypeValue = MapTypeValue(MapTypeValue(SimpleTypeValue(FLOAT_CODENAME)))
  val mapMapStringType: TypeValue = MapTypeValue(MapTypeValue(SimpleTypeValue(STRING_CODENAME)))
}
