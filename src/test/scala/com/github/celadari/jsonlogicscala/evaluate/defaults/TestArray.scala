package com.github.celadari.jsonlogicscala.evaluate.defaults

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, SimpleTypeValue, TypeValue}


trait TestArray extends AnyFlatSpec with Matchers {

  val arrBool: Array[java.lang.Boolean] = Array(true, false, true, true, false)
  val arrInt: Array[java.lang.Integer] = Array(3, 56, 78, 53, 0)
  val arrLong: Array[java.lang.Long] = Array(67L, 9L, 672L, 45L, 4L)
  val arrDouble: Array[java.lang.Double] = Array(45d, 76d, 90d, 23d, 1d)
  val arrFloat: Array[java.lang.Float] = Array(67f, 89f, 56f, 1f, 0f)
  val arrString: Array[java.lang.String] = Array("I", "love", "walking", "in", "New York")

  val arrArrBool: Array[Array[java.lang.Boolean]] = Array(Array(true, false, true), Array(true, true, true), Array(false, false, true))
  val arrArrInt: Array[Array[java.lang.Integer]] = Array(Array(34, 56, 89), Array(2, 69, 82), Array(83, 60, 4))
  val arrArrLong: Array[Array[java.lang.Long]] = Array(Array(4L, 56L, 2L), Array(94L, 6L, 78L), Array(31L, 79L, 45L))
  val arrArrDouble: Array[Array[java.lang.Double]] = Array(Array(67d, 49d, 9d), Array(3d, 23d, 87d), Array(7d, 23d, 24d))
  val arrArrFloat: Array[Array[java.lang.Float]] = Array(Array(84f, 37f, 61f), Array(22f, 8f, 75f), Array(2f, 945f, 24f))
  val arrArrString: Array[Array[java.lang.String]] = Array(
    Array("Charizard", "Dragonite", "Gyarados"),
    Array("Obelisk", "Ra", "Slifer"),
    Array("Griezmann", "Pogba", "Mbappe")
  )

  val arrListBool: Array[List[java.lang.Boolean]] = Array(List(true, false, true), List(true, true, true), List(false, false, true))
  val arrListInt: Array[List[java.lang.Integer]] = Array(List(34, 56, 89), List(2, 69, 82), List(83, 60, 4))
  val arrListLong: Array[List[java.lang.Long]] = Array(List(4L, 56L, 2L), List(94L, 6L, 78L), List(31L, 79L, 45L))
  val arrListDouble: Array[List[java.lang.Double]] = Array(List(67d, 49d, 9d), List(3d, 23d, 87d), List(7d, 23d, 24d))
  val arrListFloat: Array[List[java.lang.Float]] = Array(List(84f, 37f, 61f), List(22f, 8f, 75f), List(2f, 945f, 24f))
  val arrListString: Array[List[java.lang.String]] = Array(
    List("Charizard", "Dragonite", "Gyarados"),
    List("Obelisk", "Ra", "Slifer"),
    List("Griezmann", "Pogba", "Mbappe")
  )

  val arrBoolType: TypeValue = ArrayTypeValue(SimpleTypeValue(BOOL_CODENAME))
  val arrIntType: TypeValue = ArrayTypeValue(SimpleTypeValue(INT_CODENAME))
  val arrLongType: TypeValue = ArrayTypeValue(SimpleTypeValue(LONG_CODENAME))
  val arrDoubleType: TypeValue = ArrayTypeValue(SimpleTypeValue(DOUBLE_CODENAME))
  val arrFloatType: TypeValue = ArrayTypeValue(SimpleTypeValue(FLOAT_CODENAME))
  val arrStringType: TypeValue = ArrayTypeValue(SimpleTypeValue(STRING_CODENAME))

  val arrArrBoolType: TypeValue = ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(BOOL_CODENAME)))
  val arrArrIntType: TypeValue = ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(INT_CODENAME)))
  val arrArrLongType: TypeValue = ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(LONG_CODENAME)))
  val arrArrDoubleType: TypeValue = ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(DOUBLE_CODENAME)))
  val arrArrFloatType: TypeValue = ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(FLOAT_CODENAME)))
  val arrArrStringType: TypeValue = ArrayTypeValue(ArrayTypeValue(SimpleTypeValue(STRING_CODENAME)))
}
