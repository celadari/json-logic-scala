package com.celadari.jsonlogicscala.converters

import scala.collection.{ArrayOps, IterableOnce}
import scala.jdk.CollectionConverters.{MapHasAsScala => OriginalMapHasAsScala}
import scala.reflect.ClassTag


object CollectionConverters {

  implicit class MapHasAsScala[K, V](m: java.util.Map[K, V]) extends OriginalMapHasAsScala(m)

  implicit class HasPartitionMap[A](arr: Array[A]) {
    def partitionMapAccordingToScalaVersion[A1: ClassTag, A2: ClassTag](f: A => Either[A1, A2]): (Array[A1], Array[A2]) = new ArrayOps[A](arr).partitionMap(f)
  }

  implicit class HasMapValues[K, +V](map: scala.collection.Map[K, V]) {
    def mapValuesAccordingToScalaVersion[W](f: V => W): Map[K, W] = map.view.mapValues(f).toMap
  }

  implicit class HasFlatMap[+A](arr: Array[A]) {
    def flatMapAccordingToScalaVersion[B: ClassTag](f: A => IterableOnce[B]): Array[B] = new ArrayOps[A](arr).flatMap[B](f)
  }
}
