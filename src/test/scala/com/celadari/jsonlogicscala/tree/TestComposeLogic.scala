package com.celadari.jsonlogicscala.tree

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.celadari.jsonlogicscala.tree.types.DefaultTypes.INT_CODENAME
import com.celadari.jsonlogicscala.tree.types.SimpleTypeValue


class TestComposeLogic extends AnyFlatSpec with Matchers {

  "Instantiate ComposeLogic" should "work" in {
    noException shouldBe thrownBy {
      new ComposeLogic("+", Array(
        ValueLogic(Some(45), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data")),
        ValueLogic(Some(78), Some(SimpleTypeValue(INT_CODENAME)), None, Some("data"))
      ))
    }
  }

}
