package com.github.celadari.jsonlogicscala.refs


case class RefComposeLogic[T](operator: String, conditions: Array[RefJsonLogicCore[T]]) extends RefJsonLogicCore[T]
