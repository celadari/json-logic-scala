package com.celadari.jsonlogicscala.deserialize

import org.apache.xbean.finder.ResourceFinder
import com.celadari.jsonlogicscala.tree.types.DefaultTypes._
import com.celadari.jsonlogicscala.converters.CollectionConverters.MapHasAsScala
import com.celadari.jsonlogicscala.configuration.ConfigurationFetcher
import com.celadari.jsonlogicscala.deserialize.defaults._


object DeserializerConf {
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

case class DeserializerConf(
                             unmarshallerMetaInfAdd: Map[String, Unmarshaller],
                             unmarshallersManualAdd: Map[String, Unmarshaller],
                             isPriorityToManualAdd: Boolean = true
                           )
