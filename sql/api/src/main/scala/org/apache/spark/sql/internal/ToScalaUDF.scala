/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.spark.sql.internal

import scala.jdk.CollectionConverters._

import org.apache.spark.api.java.function.{CoGroupFunction, FilterFunction, FlatMapFunction, FlatMapGroupsFunction, FlatMapGroupsWithStateFunction, ForeachFunction, ForeachPartitionFunction, MapFunction, MapGroupsFunction, MapGroupsWithStateFunction, MapPartitionsFunction, ReduceFunction}
import org.apache.spark.sql.api.java._
import org.apache.spark.sql.streaming.GroupState

/**
 * Helper class that provides conversions from org.apache.spark.sql.api.java.Function* and
 * org.apache.spark.api.java.function.* to scala functions.
 *
 * Please note that this class is being used in Spark Connect Scala UDFs. We need to be careful
 * with any modifications to this class, otherwise we will break backwards compatibility. Concretely
 * this means you can only add methods to this class. You cannot rename the class, move it, change
 * its `serialVersionUID`, remove methods, change method signatures, or change method semantics.
 */
@SerialVersionUID(2019907615267866045L)
private[sql] object ToScalaUDF extends Serializable {
  def apply[T](f: FilterFunction[T]): T => Boolean = f.call

  def apply[T](f: ReduceFunction[T]): (T, T) => T = f.call

  def apply[V, W](f: MapFunction[V, W]): V => W = f.call

  def apply[K, V, U](f: MapGroupsFunction[K, V, U]): (K, Iterator[V]) => U =
    (key, values) => f.call(key, values.asJava)

  def apply[K, V, S, U](f: MapGroupsWithStateFunction[K, V, S, U])
    : (K, Iterator[V], GroupState[S]) => U =
    (key, values, state) => f.call(key, values.asJava, state)

  def apply[V, U](f: MapPartitionsFunction[V, U]): Iterator[V] => Iterator[U] =
    values => f.call(values.asJava).asScala

  def apply[K, V, U](f: FlatMapGroupsFunction[K, V, U]): (K, Iterator[V]) => Iterator[U] =
    (key, values) => f.call(key, values.asJava).asScala

  def apply[K, V, S, U](f: FlatMapGroupsWithStateFunction[K, V, S, U])
    : (K, Iterator[V], GroupState[S]) => Iterator[U] =
    (key, values, state) => f.call(key, values.asJava, state).asScala

  def apply[K, V, U, R](f: CoGroupFunction[K, V, U, R])
    : (K, Iterator[V], Iterator[U]) => Iterator[R] =
    (key, left, right) => f.call(key, left.asJava, right.asJava).asScala

  def apply[V](f: ForeachFunction[V]): V => Unit = f.call

  def apply[V](f: ForeachPartitionFunction[V]): Iterator[V] => Unit =
    values => f.call(values.asJava)

  // scalastyle:off line.size.limit

  /* register 0-22 were generated by this script

    (0 to 22).foreach { i =>
      val extTypeArgs = (0 to i).map(_ => "_").mkString(", ")
      val anyTypeArgs = (0 to i).map(_ => "Any").mkString(", ")
      val anyCast = s".asInstanceOf[UDF$i[$anyTypeArgs]]"
      val anyParams = (1 to i).map(_ => "_: Any").mkString(", ")
      val funcCall = if (i == 0) s"() => f$anyCast.call($anyParams)" else s"f$anyCast.call($anyParams)"
      println(s"""
        |/**
        | * Create a scala.Function$i wrapper for a org.apache.spark.sql.api.java.UDF$i instance.
        | */
        |def apply(f: UDF$i[$extTypeArgs]): Function$i[$anyTypeArgs] = {
        |  $funcCall
        |}""".stripMargin)
    }
    */

  /**
   * Create a scala.Function0 wrapper for a org.apache.spark.sql.api.java.UDF0 instance.
   */
  def apply(f: UDF0[_]): () => Any = {
    () => f.asInstanceOf[UDF0[Any]].call()
  }

  /**
   * Create a scala.Function1 wrapper for a org.apache.spark.sql.api.java.UDF1 instance.
   */
  def apply(f: UDF1[_, _]): (Any) => Any = {
    f.asInstanceOf[UDF1[Any, Any]].call(_: Any)
  }

  /**
   * Create a scala.Function2 wrapper for a org.apache.spark.sql.api.java.UDF2 instance.
   */
  def apply(f: UDF2[_, _, _]): (Any, Any) => Any = {
    f.asInstanceOf[UDF2[Any, Any, Any]].call(_: Any, _: Any)
  }

  /**
   * Create a scala.Function3 wrapper for a org.apache.spark.sql.api.java.UDF3 instance.
   */
  def apply(f: UDF3[_, _, _, _]): (Any, Any, Any) => Any = {
    f.asInstanceOf[UDF3[Any, Any, Any, Any]].call(_: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function4 wrapper for a org.apache.spark.sql.api.java.UDF4 instance.
   */
  def apply(f: UDF4[_, _, _, _, _]): (Any, Any, Any, Any) => Any = {
    f.asInstanceOf[UDF4[Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function5 wrapper for a org.apache.spark.sql.api.java.UDF5 instance.
   */
  def apply(f: UDF5[_, _, _, _, _, _]): (Any, Any, Any, Any, Any) => Any = {
    f.asInstanceOf[UDF5[Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function6 wrapper for a org.apache.spark.sql.api.java.UDF6 instance.
   */
  def apply(f: UDF6[_, _, _, _, _, _, _]): (Any, Any, Any, Any, Any, Any) => Any = {
    f.asInstanceOf[UDF6[Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function7 wrapper for a org.apache.spark.sql.api.java.UDF7 instance.
   */
  def apply(f: UDF7[_, _, _, _, _, _, _, _]): (Any, Any, Any, Any, Any, Any, Any) => Any = {
    f.asInstanceOf[UDF7[Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function8 wrapper for a org.apache.spark.sql.api.java.UDF8 instance.
   */
  def apply(f: UDF8[_, _, _, _, _, _, _, _, _]): (Any, Any, Any, Any, Any, Any, Any, Any) => Any = {
    f.asInstanceOf[UDF8[Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function9 wrapper for a org.apache.spark.sql.api.java.UDF9 instance.
   */
  def apply(f: UDF9[_, _, _, _, _, _, _, _, _, _]): (Any, Any, Any, Any, Any, Any, Any, Any, Any) => Any = {
    f.asInstanceOf[UDF9[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function10 wrapper for a org.apache.spark.sql.api.java.UDF10 instance.
   */
  def apply(f: UDF10[_, _, _, _, _, _, _, _, _, _, _]): Function10[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF10[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function11 wrapper for a org.apache.spark.sql.api.java.UDF11 instance.
   */
  def apply(f: UDF11[_, _, _, _, _, _, _, _, _, _, _, _]): Function11[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF11[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function12 wrapper for a org.apache.spark.sql.api.java.UDF12 instance.
   */
  def apply(f: UDF12[_, _, _, _, _, _, _, _, _, _, _, _, _]): Function12[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF12[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function13 wrapper for a org.apache.spark.sql.api.java.UDF13 instance.
   */
  def apply(f: UDF13[_, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function13[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF13[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function14 wrapper for a org.apache.spark.sql.api.java.UDF14 instance.
   */
  def apply(f: UDF14[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function14[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF14[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function15 wrapper for a org.apache.spark.sql.api.java.UDF15 instance.
   */
  def apply(f: UDF15[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function15[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF15[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function16 wrapper for a org.apache.spark.sql.api.java.UDF16 instance.
   */
  def apply(f: UDF16[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function16[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF16[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function17 wrapper for a org.apache.spark.sql.api.java.UDF17 instance.
   */
  def apply(f: UDF17[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function17[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF17[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function18 wrapper for a org.apache.spark.sql.api.java.UDF18 instance.
   */
  def apply(f: UDF18[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function18[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF18[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function19 wrapper for a org.apache.spark.sql.api.java.UDF19 instance.
   */
  def apply(f: UDF19[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function19[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF19[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function20 wrapper for a org.apache.spark.sql.api.java.UDF20 instance.
   */
  def apply(f: UDF20[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function20[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF20[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function21 wrapper for a org.apache.spark.sql.api.java.UDF21 instance.
   */
  def apply(f: UDF21[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function21[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF21[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }

  /**
   * Create a scala.Function22 wrapper for a org.apache.spark.sql.api.java.UDF22 instance.
   */
  def apply(f: UDF22[_, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _]): Function22[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any] = {
    f.asInstanceOf[UDF22[Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any, Any]].call(_: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any, _: Any)
  }
  // scalastyle:on line.size.limit
}

/**
 * Adaptors from one UDF shape to another. For example adapting a foreach function for use in
 * foreachPartition.
 *
 * Please note that this class is being used in Spark Connect Scala UDFs. We need to be careful
 * with any modifications to this class, otherwise we will break backwards compatibility. Concretely
 * this means you can only add methods to this class. You cannot rename the class, move it, change
 * its `serialVersionUID`, remove methods, change method signatures, or change method semantics.
 */
@SerialVersionUID(0L) // TODO
object UDFAdaptors extends Serializable {
  def flatMapToMapPartitions[V, U](f: V => IterableOnce[U]): Iterator[V] => Iterator[U] =
    values => values.flatMap(f)

  def flatMapToMapPartitions[V, U](f: FlatMapFunction[V, U]): Iterator[V] => Iterator[U] =
    values => values.flatMap(v => f.call(v).asScala)

  def mapToMapPartitions[V, U](f: V => U): Iterator[V] => Iterator[U] = values => values.map(f)

  def mapToMapPartitions[V, U](f: MapFunction[V, U]): Iterator[V] => Iterator[U] =
    values => values.map(f.call)

  def foreachToForeachPartition[T](f: T => Unit): Iterator[T] => Unit =
    values => values.foreach(f)

  def foreachToForeachPartition[T](f: ForeachFunction[T]): Iterator[T] => Unit =
    values => values.foreach(f.call)

  def foreachPartitionToMapPartitions[V, U](f: Iterator[V] => Unit): Iterator[V] => Iterator[U] =
    values => {
      f(values)
      Iterator.empty[U]
    }

  def iterableOnceToSeq[A, B](f: A => IterableOnce[B]): A => Seq[B] =
    value => f(value).iterator.toSeq

  def mapGroupsToFlatMapGroups[K, V, U](f: (K, Iterator[V]) => U): (K, Iterator[V]) => Iterator[U] =
    (key, values) => Iterator.single(f(key, values))

  def mapGroupsWithStateToFlatMapWithState[K, V, S, U](
      f: (K, Iterator[V], GroupState[S]) => U): (K, Iterator[V], GroupState[S]) => Iterator[U] =
    (key: K, values: Iterator[V], state: GroupState[S]) => Iterator(f(key, values, state))

  def coGroupWithMappedValues[K, V, U, R, IV, IU](
      f: (K, Iterator[V], Iterator[U]) => IterableOnce[R],
      leftValueMapFunc: Option[IV => V],
      rightValueMapFunc: Option[IU => U]): (K, Iterator[IV], Iterator[IU]) => IterableOnce[R] = {
    (leftValueMapFunc, rightValueMapFunc) match {
      case (None, None) =>
        f.asInstanceOf[(K, Iterator[IV], Iterator[IU]) => IterableOnce[R]]
      case (Some(mapLeft), None) =>
        (k, left, right) => f(k, left.map(mapLeft), right.asInstanceOf[Iterator[U]])
      case (None, Some(mapRight)) =>
        (k, left, right) => f(k, left.asInstanceOf[Iterator[V]], right.map(mapRight))
      case (Some(mapLeft), Some(mapRight)) =>
        (k, left, right) => f(k, left.map(mapLeft), right.map(mapRight))
    }
  }

  def flatMapGroupsWithMappedValues[K, IV, V, R](
     f: (K, Iterator[V]) => IterableOnce[R],
     valueMapFunc: Option[IV => V]): (K, Iterator[IV]) => IterableOnce[R] = valueMapFunc match {
    case Some(mapValue) => (k, values) => f(k, values.map(mapValue))
    case None => f.asInstanceOf[(K, Iterator[IV]) => IterableOnce[R]]
  }

  def flatMapGroupsWithStateWithMappedValues[K, IV, V, S, U](
      f: (K, Iterator[V], GroupState[S]) => Iterator[U],
      valueMapFunc: Option[IV => V]): (K, Iterator[IV], GroupState[S]) => Iterator[U] = {
    valueMapFunc match {
      case Some(mapValue) => (k, values, state) => f(k, values.map(mapValue), state)
      case None => f.asInstanceOf[(K, Iterator[IV], GroupState[S]) => Iterator[U]]
    }
  }
}
