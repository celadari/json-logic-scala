// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.deserialize

import org.apache.xbean.finder.ResourceFinder
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.converters.CollectionConverters.MapHasAsScala
import com.celadari.jsonlogicscala.configuration.ConfigurationFetcher
import com.celadari.jsonlogicscala.deserialize.defaults._


/**
 * Companion object to hold implicit: [[com.celadari.jsonlogicscala.deserialize.DeserializerConf]], mapping of default
 * unmarshallers (type_codename -> Unmarshaller), method to create a custom configuration.
 */
object DeserializerConf {
  /**
   * Maps default type_codenames to default unmarshallers.
   */
  val DEFAULT_UNMARSHALLERS: Map[String, Unmarshaller] = Map(
    BOOL_CODENAME -> UnmarshallerBoolean,
    DOUBLE_CODENAME -> UnmarshallerDouble,
    FLOAT_CODENAME -> UnmarshallerFloat,
    INT_CODENAME -> UnmarshallerInt,
    STRING_CODENAME -> UnmarshallerString,
    LONG_CODENAME -> UnmarshallerLong,
    BYTE_CODENAME -> UnmarshallerByte,
    SHORT_CODENAME -> UnmarshallerShort,
    NULL_CODENAME -> UnmarshallerNull
  )

  /**
   * Returns a custom deserializer configuration.
   * @param path: folder to provider-configuration files.
   * @param unmarshallersManualAdd: map of (type_codename -> Unmashaller) to be added to the deserializer configuration.
   * @param isPriorityToManualAdd: boolean indicating if unmarshallers manually added have priority over those fetched from provider-configuration folder.
   * @return custom deserializer configuration.
   */
  def createConf(
                  path: String = "META-INF/services/",
                  unmarshallersManualAdd: Map[String, Unmarshaller] = DEFAULT_UNMARSHALLERS,
                  isPriorityToManualAdd: Boolean = true
                ): DeserializerConf = {
    val finder = new ResourceFinder(path)
    val props = finder.mapAllProperties(classOf[Unmarshaller].getName).asScala
    val unmarshallersMetaInf = props.map{case (fileName, prop) => ConfigurationFetcher.getOrCreateClassFromProperties[Unmarshaller](fileName, prop)}.toMap
    DeserializerConf(unmarshallersMetaInf, unmarshallersManualAdd, isPriorityToManualAdd)
  }

  implicit val deserializerConf: DeserializerConf = createConf()
}

/**
 * Represents a deserializer's configuration.
 * It informs the deserializer how to map a type_codename to a [[com.celadari.jsonlogicscala.deserialize.Unmarshaller]].
 * @param unmarshallerMetaInfAdd: map of (type_codename -> Unmarshaller) fetched from META-INF resources folder.
 * @param unmarshallersManualAdd: map of (type_codename -> Unmarshaller) manually added to the configuration.
 * @param isPriorityToManualAdd: boolean indicating if unmarshallers manually added have priority over those fetched from META-INF folder.
 */
case class DeserializerConf(
                             unmarshallerMetaInfAdd: Map[String, Unmarshaller],
                             unmarshallersManualAdd: Map[String, Unmarshaller],
                             isPriorityToManualAdd: Boolean = true
                           )
