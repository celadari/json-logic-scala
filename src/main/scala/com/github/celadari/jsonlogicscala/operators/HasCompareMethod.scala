package com.github.celadari.jsonlogicscala.operators


object HasCompareMethod {

  abstract class HasCompareMethod[A](a: A) {
    def <[B](b: B): Boolean
    def >[B](b: B): Boolean = ! <=(b)
    def <=[B](b: B): Boolean = <(b) || a == b
    def >=[B](b: B): Boolean = >(b) || a == b
  }

  implicit class ByteComparator(a: Byte) extends HasCompareMethod[Byte](a){
    def <[B](b: B): Boolean = a < b
  }

  implicit class ShortComparator(a: Short) extends HasCompareMethod[Short](a){
    def <[B](b: B): Boolean = a < b
  }

  implicit class IntComparator(a: Int) extends HasCompareMethod[Int](a){
    def <[B](b: B): Boolean = a < b
  }

  implicit class FloatComparator(a: Float) extends HasCompareMethod[Float](a){
    def <[B](b: B): Boolean = a < b
  }

  implicit class DoubleComparator(a: Double) extends HasCompareMethod[Double](a){
    def <[B](b: B): Boolean = a < b
  }

  implicit class LongComparator(a: Long) extends HasCompareMethod[Long](a){
    def <[B](b: B): Boolean = a < b
  }

}