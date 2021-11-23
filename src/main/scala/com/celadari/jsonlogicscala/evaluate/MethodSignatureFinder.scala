// Copyright 2019 celadari. All rights reserved. MIT license.
package com.celadari.jsonlogicscala.evaluate

import java.lang.reflect.Method
import scala.collection.mutable


/**
 * Companion object that defines utility function to find minimum (class inheritance as partial order) classes
 * among set of classes.
 */
object MethodSignatureFinder {

  /**
   * Returns minimum super-classes of classOb among set of classes.
   * @example A   B     C
   *         / \ / \  /
   *        D   E   F
   *            \  /
   *             G
   *     maxMins of G is Set(E,F)
   * @param classObj: class to find minimum super-classes of.
   * @param classesToScan: set of classes to consider for minimums search.
   * @return set of minimum super-classes of classOb.
   */
  def minsSup(classObj: Class[_], classesToScan: Set[Class[_]]): Set[Class[_]] = {
    // retrieve super classes of classObj
    val closeClasses = mutable.Set[Class[_]]()
    closeClasses ++= classesToScan.filter(_.isAssignableFrom(classObj))

    val closests = mutable.Set[Class[_]]()
    while (closeClasses.nonEmpty) {
      val classToExamine = closeClasses.head
      closeClasses -= classToExamine

      // if there is no subclass of classToExamine in neither closeClasses neither closests than save it in closests
      if (!(closeClasses ++ closests).exists(classToExamine.isAssignableFrom)) closests += classToExamine
    }

    closests.toSet
  }

  /**
   * Returns minimum classes among set of classes.
   * @example class A, class B extends A, class C, class D extends B, trait E, class F extends B with E, class G extends B with E
   *          then result is set {class C, class D, class F, class G}
   * @param classObjs: set of classes to look for minimums.
   * @return set of minimums.
   */
  def mins(classObjs: Set[Class[_]]): Set[Class[_]] = {
    val visitedClasses = mutable.Set[Class[_]]()
    val toVisitClasses = mutable.Stack[Class[_]]() ++ classObjs

    while(toVisitClasses.nonEmpty) {
      val toVisitClass = toVisitClasses.pop()
      if (!(visitedClasses ++ toVisitClasses).exists(classEl => toVisitClass.isAssignableFrom(classEl))) visitedClasses += toVisitClass
    }
    visitedClasses.toSet
  }

  /**
   * Returns tuple of (set of classes, array).
   * Set of classes corresponds to set of minimum classes of common ancestor class of all elements in values.
   * Returned array is values whose elements are casted as common ancestor.
   * @param values: array to look for element's common ancestor.
   * @param classesToScan: set of classes to consider for minimums search.
   * @return tuple of (minimum_classes, casted_values).
   */
  def minsSupAndCastedValues(values: Array[Any], classesToScan: Set[Class[_]]): (Set[Class[_]], Array[Any]) = {
    // check different types of arrClassObj
    val arrClassObj = values.map(_.getClass)
    val parentClasses = mins(arrClassObj.toSet)
    val parentClass = if (parentClasses.size >= 2) classOf[java.lang.Object] else parentClasses.head

    // retrieve assignable classes of values
    val parentArr = java.lang.reflect.Array.newInstance(parentClass, values.length)
    val parentArrClass = parentArr.getClass
    for (idx <- values.indices) java.lang.reflect.Array.set(parentArr, idx, values(idx))

    (minsSup(parentArrClass, classesToScan), parentArr.asInstanceOf[Array[Any]])
  }

}


/**
 * Responsible for finding paths of a compatible classes in case there exists multiple overloaded method versions
 * of operator's method.
 * @example
 * class A, class B extends A
 * conditionsValues(a, b, c) (a object of A, b object of B, c object of C)
 * overloaded methods have the following signatures:
 * overloaded_method(A, A): B
 * overloaded_method(A, B): A
 * overloaded_method(A, C): D
 * overloaded_method(A, D): A
 * overloaded_method(B, A): A
 * overloaded_method(B, C): A
 * overloaded_method(D, C): A
 *
 * then compatible paths are
 * Array((overloaded_method(A, A): B), (overloaded_method(B, C): A))
 * Array((overloaded_method(A, B): A), (overloaded_method(A, C): D))
 *
 * This search is used on reduce type operators as conditions might not be the same type and which overloaded method
 * to be applied must be found before evaluation.
 */
class MethodSignatureFinder(
                             val conditionsValues: Array[Any],
                             val confMethod: MethodConf
                           ) {
  protected[this] val conditionsValuesEval: mutable.Stack[Any] = mutable.Stack[Any]() ++ conditionsValues
  protected[this] var isEvaluated: Boolean = false
  protected[this] var paths: Set[Array[(Method, Boolean)]] = Set()
  initializePaths()

  /**
   * Initiates parameters values before paths search.
   * Required as first search compares classes/types of "conditionsValues"'s first element and second element while
   * next searches compares classes/types of last element of "paths" and current element of "conditionsValues".
   */
  protected[this] def initializePaths(): Unit = {
    val condValueEval1 = conditionsValuesEval.pop()
    val condValueEval2Opt = if (conditionsValuesEval.headOption.nonEmpty) Some(conditionsValuesEval.pop()) else None

    val valueClassMethods = findFirstMethods(condValueEval1, condValueEval1, condValueEval2Opt)
    val classMethods = if (valueClassMethods.nonEmpty) valueClassMethods else findFirstMethods(confMethod.ownerMethodOpt.get, condValueEval1, condValueEval2Opt)

    paths ++= classMethods.map(method => Array((method, valueClassMethods.nonEmpty)))
  }

  /**
   * Returns set of overloaded methods that are compatible with value1 and value2Opt (if defined) as input.
   * @param ownerMethod: object the methods belong to.
   * @param value1: first input to search for compatibility among overloaded methods.
   * @param value2Opt: second input to search for compatibility among overloaded methods.
   * @return set of overloaded methods.
   */
  protected[this] def findFirstMethods(ownerMethod: Any, value1: Any, value2Opt: Option[Any]): Set[Method] = {
    ownerMethod
      .getClass
      .getMethods
      .filter(_.getName == confMethod.methodName)
      .filter(method => {
        val class1 = method.getParameterTypes.apply(0)
        val class2Opt = method.getParameterTypes.lift(1)
        val isParam2Compatible = value2Opt
          .map(value2 => class2Opt.exists(_.isInstance(value2)))
          .getOrElse(class2Opt.isEmpty)

        class1.isInstance(value1) && isParam2Compatible
      })
      .toSet
  }

  /**
   * Returns set of overloaded methods of "objClass" that are compatible with return type of the last method
   * of "path" and "value" as first and second input.
   * @param objClass: object overloaded methods belong to.
   * @param value: value to check class/type's compatibility with overloaded methods signature.
   * @param path: array to check last element's class/type compatibility with overloaded methods signature.
   * @return set of overloaded methods.
   */
  protected[this] def filterExplorable(objClass: Class[_], value: Any, path: Array[(Method, Boolean)]): Set[Method] = {
    val valueClass = value.getClass

    objClass
      .getMethods
      .toSet
      .filter(_.getName == confMethod.methodName)
      .filter(method => {
        val class1 = method.getParameterTypes.apply(0)
        val class2 = method.getParameterTypes.apply(1)
        class1.isAssignableFrom(path.last._1.getReturnType) && class2.isAssignableFrom(valueClass)
      })
  }

  /**
   * Returns set of paths.
   * Set of paths corresponds to the different possible paths that extend "path" by adding an overloaded method
   * compatible with "path"'s last element (class/type is [[com.celadari.jsonlogicscala.evaluate.MethodConf]]) return
   * type as first input and value as second input.
   * @param value: value to check class/type's compatibility with overloaded methods signature.
   * @param ownerMethodOpt: object overloaded methods belong to.
   * @param path: array of methods to be extended.
   * @return set of compatible paths.
   */
  protected[this] def explorePath(
                                 value: Any,
                                 ownerMethodOpt: Option[Class[_]],
                                 path: Array[(Method, Boolean)]
                               ): Set[Array[(Method, Boolean)]] = {
    val valueClassMethods = filterExplorable(value.getClass, value, path)
    val classMethods = if (valueClassMethods.nonEmpty) valueClassMethods else filterExplorable(ownerMethodOpt.get, value, path)
    classMethods.map(method => path ++ Array((method, valueClassMethods.nonEmpty)))
  }

  /**
   * Triggers search for compatible paths with "conditionsValues" and "confMethod".
   * @return set of compatible paths.
   */
  def eval(): Unit = {
    while (conditionsValuesEval.nonEmpty) {
      val value = conditionsValuesEval.pop()
      paths = paths.flatMap(explorePath(value, confMethod.ownerMethodOpt.map(_.getClass), _: Array[(Method, Boolean)]))
    }

    isEvaluated = true
  }

  /**
   * Optimizes found paths by removing a path if there exists another path with a method with sub-types input arguments.
   * @example
   * class A, class B extends A, class C, class D extends C, class E
   * path1: Array((method(A, C), _), ...)
   * path2: Array((method(A, D), _), ...)
   * path3: Array((method(B, C), _), ...)
   * path4: Array((method(B, D), _), ...)
   * path5: Array((method(A, E), _), ...)
   *
   * optimized paths are
   * path1: Array((method(B, D), _), ...)
   * path5: Array((method(A, E), _), ...)
   */
  def optimizePaths(): Unit = {
    val n = paths.headOption.map(_.length).getOrElse(0)

    for (i <- 0 until n) {
      val methodsToExplore = mutable.Set[(Method, Boolean)]() ++ paths.map(_(i))

      val mostSpecificMethods = mutable.Set[(Method, Boolean)]()
      while (methodsToExplore.nonEmpty) {
        val (methodToExplore, isMethodOwnedByReducedValue) = methodsToExplore.head
        methodsToExplore -= ((methodToExplore, isMethodOwnedByReducedValue))

        val isSuperClassOfAnotherClass = (methodsToExplore ++ mostSpecificMethods).exists{case (method, _) => {
          methodToExplore.getParameterTypes.apply(0).isAssignableFrom(method.getParameterTypes.apply(0)) &&
          methodToExplore.getParameterTypes.apply(1).isAssignableFrom(method.getParameterTypes.apply(1))
        }}
        if (!isSuperClassOfAnotherClass) mostSpecificMethods += ((methodToExplore, isMethodOwnedByReducedValue))
      }

      paths = paths.filter(path => mostSpecificMethods.contains(path(i)))
    }
  }

  /**
   * Returns set of optimized compatible paths with "conditionsValues" and "confMethod".
   * If search has not yet been triggered, it is triggered, otherwise returns cached value.
   * @return set of compatible paths.
   */
  def findPaths(): Set[Array[(Method, Boolean)]] = {
    if (!isEvaluated) {
      eval()
      optimizePaths()
    }
    paths
  }
}
