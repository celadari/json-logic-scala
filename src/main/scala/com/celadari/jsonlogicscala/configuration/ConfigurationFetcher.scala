// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.configuration

import java.util.Properties
import scala.reflect.runtime.{universe => ru}
import org.apache.xbean.recipe.{MissingAccessorException, ObjectRecipe}
import com.celadari.jsonlogicscala.exceptions.ConfigurationException


/**
 * Contains methods to instantiate object from configuration properties (Marshaller|Unmarshaller|EvaluatorValueLogic)
 */
object ConfigurationFetcher {

  /**
   * Returns boolean property from "java.util.Properties" object or else defaultValue.
   * @param key: key to look for in prop.
   * @param defaultValue: value to return is key doesn't exist in prop.
   * @param prop: configuration to look key into.
   * @param throwable: exception to be thrown if needed.
   * @return boolean property value or defaultValue.
   */
  def getOptionalBooleanProperty(key: String, defaultValue: Boolean, prop: Properties, throwable: Throwable): Boolean = {
    try {
      if (prop.containsKey(key)) prop.remove(key).toString.toBoolean else defaultValue
    }
    catch {
      case _: Throwable => throw throwable
    }
  }

  /**
   * Returns tuple of (typeCodename, value) from configuration file.
   * @param fileName: path of configuration file.
   * @param prop: read properties from configuration file.
   * @tparam T: object to be instantiated after reading configuration.
   * @return instantiated object after reading configuration.
   */
  def getOrCreateClassFromProperties[T: ru.TypeTag](fileName: String, prop: Properties): (String, T) = {
    if (!prop.containsKey("className")) throw new ConfigurationException(s"Property file '$fileName' must define key 'className'")
    if (!prop.containsKey("codename")) throw new ConfigurationException(s"Property file '$fileName' must define key 'codename'")
    val className = prop.remove("className").toString
    val typeCodename = prop.remove("codename").toString

    val isObject = getOptionalBooleanProperty(
      "singleton",
      defaultValue= false,
      prop,
      new ConfigurationException(s"Property 'singleton' in property file '$fileName' is not a valid boolean parameter")
    )

    val m = ru.runtimeMirror(getClass.getClassLoader)
    if (isObject) {
      try {
        val instance = m.reflectModule(m.staticModule(className)).instance
        if (!(m.reflect(instance).symbol.toType <:< ru.typeOf[T])) {
          throw new ConfigurationException(s"Found object is not a '${ru.typeOf[T].toString}' instance:\n'$instance'")
        }
        (typeCodename, instance.asInstanceOf[T])
      }
      catch {
        case confException: ConfigurationException => throw confException
        case _: Throwable => {
          throw new ConfigurationException(s"No singleton object found for: '$className'\nCheck if 'className' '$className' is correct and if 'singleton' " +
            s"property in '$fileName' property file is correct")
        }
      }
    }
    else {
      try {
        val objectRecipe = new ObjectRecipe(className)
        val sep = if (prop.containsKey("sep")) prop.remove("sep").toString else ";"
        if (prop.containsKey("constructorArgNames")) objectRecipe.setConstructorArgNames(prop.remove("constructorArgNames").toString.split(sep))
        objectRecipe.setAllProperties(prop)
        val instance = objectRecipe.create()
        if (!(m.reflect(instance).symbol.toType <:< ru.typeOf[T])) {
          throw new ConfigurationException(s"Found class is not a '${ru.typeOf[T].toString}' instance:\n'$instance'")
        }
        (typeCodename, instance.asInstanceOf[T])
      }
      catch {
        case confException: ConfigurationException => throw confException
        case _: MissingAccessorException => {
          throw new ConfigurationException(s"Field error, check that no field in '$className' is missing in '$fileName' property file.\nCheck that no " +
            s"property in '$fileName' file is not undefined in '$className' class.\nCheck if '$className' class constructor requires arguments or " +
            s"if argument names defined in '$fileName' property file are correct")
        }
        case _: Throwable => {
          throw new ConfigurationException(s"No class found for: '$className'\nCheck if 'className' '$className' is correct and if 'singleton' " +
            s"property in '$fileName' property file is correct")
        }
      }
    }
  }
}
