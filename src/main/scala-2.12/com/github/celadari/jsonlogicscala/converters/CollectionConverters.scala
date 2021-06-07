package com.github.celadari.jsonlogicscala.converters

import scala.collection.{GenTraversableOnce, JavaConverters, mutable}
import scala.reflect.ClassTag

object CollectionConverters {

  implicit class MapHasAsScala[K, V](m: java.util.Map[K, V]) {
    def asScala: mutable.Map[K, V] = JavaConverters.mapAsScalaMap(m)
  }

  implicit class HasPartitionMap[A](arr: Array[A]) {
    def partitionMapAccordingToScalaVersion[A1: ClassTag, A2: ClassTag](f: A => Either[A1, A2]): (Array[A1], Array[A2]) = {
      val (arr1, arr2) = arr.map(f).partition(_.isLeft)
      (arr1.map(_.left.get), arr2.map(_.right.get))
    }
  }

  implicit class HasMapValues[K, V](map: scala.collection.Map[K, V]) {
    def mapValuesAccordingToScalaVersion[W](f: V => W): Map[K, W] = map.mapValues(f).toMap
  }

  implicit class HasFlatMap[+A](arr: Array[A]) {
    def flatMapAccordingToScalaVersion[B: ClassTag](f: A => GenTraversableOnce[B]): Array[B] = arr.flatMap[B, Array[B]](f)
  }
}
