package com.celadari.jsonlogicscala

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.{Matcher, MatchResult}
import org.scalatest.matchers.should.Matchers
import com.celadari.jsonlogicscala.tree.{ComposeLogic, JsonLogicCore, ValueLogic, VariableLogic}


trait JsonLogicCoreMatcher extends AnyFlatSpec with Matchers {

  // scalastyle:off cyclomatic.complexity
  def compareObject(value1: Any, value2: Any): Boolean = {
    value1 match {
      case arr1: Array[_] => {
        value2 match {
          case arr2: Array[_] => (arr1.length == arr2.length) && arr1.zip(arr2).forall{case (el1, el2) => compareObject(el1, el2)}
          case _ => false
        }
      }
      case opt1: Option[_] => {
        value2 match {
          case opt2: Option[_] => (opt1.isEmpty && opt2.isEmpty) || (opt1.isDefined && opt2.isDefined && compareObject(opt1.get, opt2.get))
          case _ => false
        }
      }
      case map1: Map[_, _] => {
        value2 match {
          case map2: Map[_, _] => {
            (map1.size == map2.size) && (map1.keySet == map2.keySet) &&
              map1.asInstanceOf[Map[Any, Any]].forall{case (key1, value1) => map2.asInstanceOf[Map[Any, Any]](key1) == value1}
          }
          case _ => false
        }
      }
      case other => other == value2
    }
  }

  class BeEqualJsonLogicCore(right: JsonLogicCore) extends Matcher[JsonLogicCore] {

    def compare(left: ValueLogic[_], right: JsonLogicCore): Boolean = {
      right match {
        case _: ComposeLogic => false
        case _: VariableLogic => false
        case valueLogic: ValueLogic[_] => {
          compareObject(left.valueOpt, valueLogic.valueOpt) &&
          left.operator == valueLogic.operator &&
          left.pathNameOpt == valueLogic.pathNameOpt &&
          left.variableNameOpt == valueLogic.variableNameOpt &&
          left.typeOpt == valueLogic.typeOpt
        }
      }
    }

    def compare(left: VariableLogic, right: JsonLogicCore): Boolean = {
      right match {
        case _: ComposeLogic => false
        case variableLogic: VariableLogic => left == variableLogic
        case _: ValueLogic[_] => false
      }
    }

    def compare(left: ComposeLogic, right: JsonLogicCore): Boolean = {
      right match {
        case composeLogic: ComposeLogic => {
          (left.operator == composeLogic.operator) &&
          (left.conditions.length == composeLogic.conditions.length) &&
          left.conditions.zip(composeLogic.conditions).forall{case (condition1, condition2) => compare(condition1, condition2)}
        }
        case _: VariableLogic => false
        case _: ValueLogic[_] => false
      }
    }

    def compare(left: JsonLogicCore, right: JsonLogicCore): Boolean = {
      left match {
        case composeLogic: ComposeLogic => compare(composeLogic, right)
        case variableLogic: VariableLogic => compare(variableLogic, right)
        case valueLogic: ValueLogic[_] => compare(valueLogic, right)
      }
    }

    override def apply(left: JsonLogicCore): MatchResult = {
      val isSuccess = compare(left, right)
      MatchResult(matches = isSuccess, "JsonLogicCore objects are not the same", "JsonLogicCore objects are equal")
    }
  }

  // scalastyle:off method.name
  def BeEqualJsonLogicCore(right: JsonLogicCore): BeEqualJsonLogicCore = new BeEqualJsonLogicCore(right)

}

object JsonLogicCoreMatcher extends JsonLogicCoreMatcher
