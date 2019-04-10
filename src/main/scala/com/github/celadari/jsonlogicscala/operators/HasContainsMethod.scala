package com.github.celadari.jsonlogicscala.operators

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import java.lang.CharSequence

object HasContainsMethod {
  abstract class HasContainsMethod[A](a: A) {
    def contains[B](b: B): Boolean
  }

  implicit class ArrayHasContainsMethod(arr: Array[_]) extends HasContainsMethod[Array[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class ArrayBufferHasContainsMethod(arr: ArrayBuffer[_]) extends HasContainsMethod[ArrayBuffer[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class ListHasContainsMethod(arr: List[_]) extends HasContainsMethod[List[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class ListBufferHasContainsMethod(arr: ListBuffer[_]) extends HasContainsMethod[ListBuffer[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class StringBuilderHasContainsMethod(arr: StringBuilder) extends HasContainsMethod[StringBuilder](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class VectorHasContainsMethod(arr: Vector[_]) extends HasContainsMethod[Vector[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class MutableStackHasContainsMethod(arr: scala.collection.mutable.Stack[_]) extends HasContainsMethod[scala.collection.mutable.Stack[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class ImmutableQueueHasContainsMethod(arr: scala.collection.immutable.Queue[_]) extends HasContainsMethod[scala.collection.immutable.Queue[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class MutableQueueHasContainsMethod(arr: scala.collection.mutable.Queue[_]) extends HasContainsMethod[scala.collection.mutable.Queue[_]](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }

  implicit class RangeHasContainsMethod(arr: Range) extends HasContainsMethod[Range](arr) {
    def contains[B](b: B): Boolean = arr.contains(b)
  }


}
