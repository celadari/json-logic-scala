package com.github.celadari.jsonlogicscala.serialize

import org.apache.xbean.finder.ResourceFinder
import com.github.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.github.celadari.jsonlogicscala.serialize.defaults._
import com.github.celadari.jsonlogicscala.configuration.ConfigurationFetcher
import com.github.celadari.jsonlogicscala.converters.CollectionConverters.MapHasAsScala


object SerializerConf {
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

case class SerializerConf(
                           marshallerMetaInfAdd: Map[String, Marshaller],
                           marshallersManualAdd: Map[String, Marshaller],
                           isPriorityToManualAdd: Boolean = true
                         )
