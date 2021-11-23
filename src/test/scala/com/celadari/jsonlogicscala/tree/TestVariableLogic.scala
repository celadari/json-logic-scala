// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.celadari.jsonlogicscala.evaluate.defaults.{TestArray, TestNumeric}
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue


class TestVariableLogic extends AnyFlatSpec with Matchers with TestArray with TestNumeric {

  "Compare same VariableLogic" should "return true" in {
    val composeLogic = new ComposeLogic("map", Array(
      ValueLogic(Some(arrInt), Some(arrIntType), None),
      new ComposeLogic("+", Array(
        ValueLogic(Some(xInt), Some(SimpleTypeValue(INT_CODENAME)), None),
        ValueLogic(None, None, Some(""), None)
      ))
    ))
    val variableLogic1 = VariableLogic("", composeLogic)
    val variableLogic2 = VariableLogic("", composeLogic)
    composeLogic.conditions(1).asInstanceOf[ComposeLogic].conditions(1) = variableLogic1
    variableLogic1 shouldBe variableLogic2
  }

}
