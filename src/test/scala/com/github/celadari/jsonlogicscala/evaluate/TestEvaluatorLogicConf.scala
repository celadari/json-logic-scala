package com.github.celadari.jsonlogicscala.evaluate

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes.{DOUBLE_CODENAME, INT_CODENAME, STRING_CODENAME}
import com.github.celadari.jsonlogicscala.tree.types.{ArrayTypeValue, SimpleTypeValue}
import com.github.celadari.jsonlogicscala.evaluate.EvaluatorLogicConf.DEFAULT_METHOD_CONFS
import com.github.celadari.jsonlogicscala.evaluate.impl._
import com.github.celadari.jsonlogicscala.exceptions.ConfigurationException


class TestEvaluatorLogicConf extends AnyFlatSpec with Matchers {

  "createConf default path" should "return conf" in {
    val result = EvaluatorLogicConf.createConf()
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater)),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), isReduceTypeOperator = false)
      ),
      DEFAULT_METHOD_CONFS,
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map()
    )

    result shouldBe expectedResult
  }

  "createConf EvaluateValueLogic other path with manual add priority" should "return conf" in {
    val result = EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/normal/manual-add-priority/",
      evaluatorValueLogicManualAdd = Map(SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble)
    )
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater)),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), isReduceTypeOperator = false)
      ),
      DEFAULT_METHOD_CONFS,
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map(
        SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble
      )
    )

    result shouldBe expectedResult
  }

  "createConf EvaluateValueLogic other path with meta-inf-priority" should "return conf" in {
    val result = EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/normal/meta-inf-priority/",
      evaluatorValueLogicManualAdd = Map(SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble),
      isPriorityToManualAdd = false
    )
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater)),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), isReduceTypeOperator = false)
      ),
      DEFAULT_METHOD_CONFS,
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map(
        SimpleTypeValue(DOUBLE_CODENAME) -> EvaluatorValueLogicImplDouble
      ),
      isPriorityToManualAdd = false
    )

    result shouldBe expectedResult
  }

  "createConf EvaluateValueLogic non EvaluateValueLogic object" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/cast-exception-singleton/"
    )}
    val expectedMessage =
      """Found object is not a 'com\.github\.celadari\.jsonlogicscala\.evaluate\.EvaluatorValueLogic' instance:
        |'(?:class\s)?com\.github\.celadari\.jsonlogicscala\.deserialize\.impl\.UnmarshallerIntImpl\$'""".stripMargin
    thrown.getMessage should fullyMatch regex expectedMessage
  }

  "createConf EvaluateValueLogic non singleton with singleton true in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/non-singleton-exception-singleton-set-to-true/"
    )}
    val expectedMessage =
      """No singleton object found for: 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplInt'
        |Check if 'className' 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplInt' is correct and if 'singleton'""".stripMargin +
        """ property in 'int' property file is correct""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic non marshaller class" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/cast-exception-class/"
    )}
    val expectedMessage =
      """Found class is not a 'com\.github\.celadari\.jsonlogicscala\.evaluate\.EvaluatorValueLogic' instance:
        |'(?:class\s)?com\.github\.celadari\.jsonlogicscala\.deserialize\.impl\.UnmarshallerDoubleImpl'""".stripMargin
    thrown.getMessage should fullyMatch regex expectedMessage
  }

  "createConf EvaluateValueLogic singleton with singleton false in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/singleton-exception-singleton-set-to-false/"
    )}
    val expectedMessage =
      """No class found for: 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplDouble'
        |Check if 'className' 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplDouble' is correct and if 'singleton'""".stripMargin +
        """ property in 'double' property file is correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic className not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/classname-not-defined-exception-class/"
    )}
    val expectedMessage = "Property file 'double' must define key 'className'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic codename not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/codename-not-defined-exception-class/"
    )}
    val expectedMessage = "Property file 'int' must define key 'codename'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic wrong constructor argument names in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic =
        "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/wrong-constructor-argument-names-definition-exception-class/"
    )}
    val expectedMessage =
      """Field error, check that no field in 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplFloat' is missing in""" +
      """ 'float' property file.
        |Check that no property in 'float' file is not undefined in 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplFloat' class.
        |Check if 'com.github.celadari.jsonlogicscala.evaluate.impl.EvaluatorValueLogicImplFloat' class constructor requires arguments or if""".stripMargin +
        """ argument names defined in 'float' property file are correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf EvaluateValueLogic wrong property type in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathEvaluatorLogic = "META-INF/services/json-logic-scala/tests/evaluator-value-logic/exceptions/wrong-property-type-exception-class/"
    )}
    val expectedMessage = "Property 'singleton' in property file 'string' is not a valid boolean parameter"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf MethodConf other path with manual add priority" should "return conf" in {
    val operatorSurround = new OperatorImplSurroundString("before", "after")
    val result = EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/normal/manual-add-priority/",
      operatorToOwnerManualAdd = Map("surround" -> operatorSurround),
      methodConfsManualAdd = Map("length" -> MethodConf("length", "length", Some(OperatorImplArrayLength), isReduceTypeOperator = false))
    )
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater)),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), isReduceTypeOperator = false),
        "surround" -> MethodConf("surround", "surroundString", Some(operatorSurround), isReduceTypeOperator = false)
      ),
      Map("length" -> MethodConf("length", "length", Some(OperatorImplArrayLength), isReduceTypeOperator = false)),
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map()
    )

    result shouldBe expectedResult
  }

  "createConf MethodConf other path with meta inf add priority" should "return conf" in {
    val result = EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/normal/meta-inf-priority/",
      operatorToOwnerManualAdd = Map(),
      methodConfsManualAdd = Map("length" -> MethodConf("length", "length", Some(OperatorImplArrayLength), isReduceTypeOperator = false)),
      isPriorityToManualAdd = false
    )
    val expectedResult = EvaluatorLogicConf(
      Map(
        "gt" -> MethodConf("gt", "greater", Some(OperatorImplGreater)),
        "prefix" -> MethodConf("prefix", "prefix", Some(new OperatorImplPrefix("before")), isReduceTypeOperator = false)
      ),
      Map("length" -> MethodConf("length", "length", Some(OperatorImplArrayLength), isReduceTypeOperator = false)),
      Map(
        SimpleTypeValue(INT_CODENAME) -> new EvaluatorValueLogicImplInt(0),
        SimpleTypeValue(STRING_CODENAME) -> EvaluatorValueLogicImplString,
        ArrayTypeValue(SimpleTypeValue(INT_CODENAME)) -> EvaluatorValueLogicImplArrayInt
      ),
      Map(),
      isPriorityToManualAdd = false
    )

    result shouldBe expectedResult
  }

  "createConf MethodConf non EvaluateValueLogic object" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/cast-exception-singleton/"
    )}
    val expectedMessage =
      """Found object is not 'com\.github\.celadari\.jsonlogicscala\.evaluate\.Operator' type:
        |(?:class\s)?com\.github\.celadari\.jsonlogicscala\.evaluate\.impl\.EvaluatorValueLogicImplString\$ cannot be cast to""".stripMargin +
        """ (?:class\s)?com\.github\.celadari\.jsonlogicscala\.evaluate\.Operator[\S\s]*""".stripMargin
    thrown.getMessage should fullyMatch regex expectedMessage
  }

  "createConf MethodConf non singleton with singleton true in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/non-singleton-exception-singleton-set-to-true/"
    )}
    val expectedMessage =
      """No singleton object found for: 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplPrefix'
        |Check if 'ownerMethod' 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplPrefix' is correct and if 'singleton' property""".stripMargin +
        """ in 'prefix' property file is correct""".stripMargin
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf MethodConf non marshaller class" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/cast-exception-class/"
    )}
    val expectedMessage =
      """Found class not 'com\.github\.celadari\.jsonlogicscala\.evaluate\.Operator' instance:
        |(?:class\s)?com\.github\.celadari\.jsonlogicscala\.evaluate\.impl\.EvaluatorValueLogicImplInt cannot be cast to""".stripMargin +
        """ (?:class\s)?com\.github\.celadari\.jsonlogicscala\.evaluate\.Operator([\s\S])*"""
    thrown.getMessage should fullyMatch regex expectedMessage
  }

  "createConf MethodConf singleton with singleton false in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/singleton-exception-singleton-set-to-false/"
    )}
    val expectedMessage =
      """No class found for: 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplGreater'
        |Check if 'ownerMethod' 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplGreater' is correct and if 'singleton'""".stripMargin +
        """ property in 'gt' property file is correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf MethodConf operator not defined in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/operator-not-defined-exception-class/"
    )}
    val expectedMessage = "Property file 'prefix' must define key 'operator'"
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf MethodConf wrong constructor argument names in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/wrong-constructor-argument-names-definition-exception-class/"
    )}
    val expectedMessage = """Field error, check that no field in 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplSurroundString' is""" +
      """ missing in 'surround' property file.
        |Check that no property in 'surround' file is not undefined in 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplSurroundString' class.
        |Check if 'com.github.celadari.jsonlogicscala.evaluate.impl.OperatorImplSurroundString' class constructor requires arguments or if""".stripMargin +
        """ argument names defined in 'surround' property file are correct"""
    thrown.getMessage shouldBe expectedMessage
  }

  "createConf MethodConf wrong property type in props file" should "throw an exception" in {
    val thrown = the[ConfigurationException] thrownBy {EvaluatorLogicConf.createConf(
      pathOperator = "META-INF/services/json-logic-scala/tests/method-conf/exceptions/wrong-property-type-exception-class/"
    )}
    val expectedMessage = "Property 'singleton' in property file 'gt' is not a valid boolean parameter"
    thrown.getMessage shouldBe expectedMessage
  }

}
