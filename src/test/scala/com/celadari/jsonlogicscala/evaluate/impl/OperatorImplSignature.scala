package com.celadari.jsonlogicscala.evaluate.impl

import com.celadari.jsonlogicscala.evaluate.Operator


object OperatorImplSignature extends Operator {

  trait GrandParent {var num: Int = 0}
  trait Parent extends GrandParent
  trait Child1 extends Parent
  trait Child2 extends Parent
  class GrandChild extends Child1 with Child2
  class Cousin extends Parent

  def doOperation(value1: GrandParent, value2: GrandParent): GrandParent = new GrandParent {num = 45}
  def doOperation(value1: Parent, value2: GrandParent): Parent = new Parent {num = 55}
  def doOperation(value1: Child1, value2: GrandParent): Child1 = new Child1 {num = 65}
  def doOperation(value1: Child2, value2: GrandParent): Child2 = new Child2 {num = 75}
  def doOperation(value1: Cousin, value2: GrandParent): Cousin = new Cousin{num = -75}

  def doOperation(value1: GrandParent, value2: Parent): GrandParent = new GrandParent {num = 85}
  def doOperation(value1: Parent, value2: Parent): Parent = new Parent {num = 95}
  def doOperation(value1: Child1, value2: Parent): Child1 = new Child1 {num = 105}
  def doOperation(value1: Child2, value2: Parent): Child2 = new Child2 {num = 115}
  def doOperation(value1: Cousin, value2: Parent): Cousin = new Cousin{num = -115}

  def doOperation(value1: GrandParent, value2: Child1): GrandParent = new GrandParent {num = 125}
  def doOperation(value1: Parent, value2: Child1): Parent = new Parent {num = 135}
  def doOperation(value1: Child1, value2: Child1): Child1 = new Child1 {num = 145}
  def doOperation(value1: Child2, value2: Child1): Child1 = new Child1 {num = 155}
  def doOperation(value1: Cousin, value2: Child1): Cousin = new Cousin{num = -155}

  def doOperation(value1: GrandParent, value2: Child2): GrandParent = new GrandParent {num = 165}
  def doOperation(value1: Parent, value2: Child2): Parent = new Parent {num = 175}
  def doOperation(value1: Child1, value2: Child2): Child1 = new Child1 {num = 185}
  def doOperation(value1: Child2, value2: Child2): Child2 = new Child2 {num = 195}
  def doOperation(value1: Cousin, value2: Child2): Cousin = new Cousin{num = -195}
}
