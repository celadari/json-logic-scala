// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.serialize

import org.apache.xbean.finder.ResourceFinder
import com.celadari.jsonlogicscala.configuration.ConfigurationFetcher
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.serialize.defaults._
import com.celadari.jsonlogicscala.converters.CollectionConverters.MapHasAsScala


/**
 * Companion object to hold implicit: [[com.celadari.jsonlogicscala.serialize.SerializerConf]], mapping of default
 * marshallers (type_codename -> Marshaller), method to create a custom configuration.
 */
object SerializerConf {
  /**
   * Maps default type_codenames to default marshallers.
   */
  val DEFAULT_MARSHALLERS = Map(
    BOOL_CODENAME -> MarshallerBoolean,
    DOUBLE_CODENAME -> MarshallerDouble,
    FLOAT_CODENAME -> MarshallerFloat,
    INT_CODENAME -> MarshallerInt,
    STRING_CODENAME -> MarshallerString,
    LONG_CODENAME -> MarshallerLong,
    BYTE_CODENAME -> MarshallerByte,
    SHORT_CODENAME -> MarshallerShort,
    NULL_CODENAME -> MarshallerNull
  )

  /**
   * Returns a custom serializer configuration.
   * @param path: folder to provider-configuration files.
   * @param marshallersManualAdd: map of (type_codename -> Mashaller) to be added to the serializer configuration.
   * @param isPriorityToManualAdd: boolean indicating if marshallers manually added have priority over those fetched from provider-configuration folder.
   * @return custom serializer configuration.
   */
  def createConf(
                  path: String = "META-INF/services/",
                  marshallersManualAdd: Map[String, Marshaller] = DEFAULT_MARSHALLERS,
                  isPriorityToManualAdd: Boolean = true
                ): SerializerConf = {
    val finder = new ResourceFinder(path)
    val props = finder.mapAllProperties(classOf[Marshaller].getName).asScala
    val marshallersMetaInf = props.map{case (fileName, prop) => ConfigurationFetcher.getOrCreateClassFromProperties[Marshaller](fileName, prop)}.toMap
    SerializerConf(marshallersMetaInf, marshallersManualAdd, isPriorityToManualAdd)
  }

  implicit val serializerConf: SerializerConf = createConf()
}

/**
 * Represents a serializer's configuration.
 * It informs the serializer how to map a type_codename to a [[com.celadari.jsonlogicscala.serialize.Marshaller]].
 * @param marshallerMetaInfAdd: map of (type_codename -> Marshaller) fetched from META-INF resources folder.
 * @param marshallersManualAdd: map of (type_codename -> Marshaller) manually added to the configuration.
 * @param isPriorityToManualAdd: boolean indicating if marshallers manually added have priority over those fetched from META-INF folder.
 */
case class SerializerConf(
                           marshallerMetaInfAdd: Map[String, Marshaller],
                           marshallersManualAdd: Map[String, Marshaller],
                           isPriorityToManualAdd: Boolean = true
                         )
