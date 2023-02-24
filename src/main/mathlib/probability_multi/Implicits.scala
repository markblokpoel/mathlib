package mathlib.probability_multi

import mathlib.probability_multi.datastructures.{
  BigNatural,
  DiscreteConditional,
  DiscreteConditionalDistributionValueAssignment,
  DiscreteDistributionValueAssignment,
  DistributionValueAssignment
}

import scala.language.implicitConversions
import scala.collection.generic.IsIterable

object Implicits {

  implicit object BigNaturalNumeric extends Numeric[BigNatural] {
    override def plus(x: BigNatural, y: BigNatural): BigNatural  = x + y
    override def minus(x: BigNatural, y: BigNatural): BigNatural = x - y
    override def times(x: BigNatural, y: BigNatural): BigNatural = x * y
    override def negate(x: BigNatural): BigNatural               = -x
    override def fromInt(x: Int): BigNatural                     = BigNatural(x)
    override def toInt(x: BigNatural): Int                       = x.toInt
    override def toLong(x: BigNatural): Long                     = x.toLong
    override def toFloat(x: BigNatural): Float                   = x.toFloat
    override def toDouble(x: BigNatural): Double                 = x.toDouble
    override def compare(x: BigNatural, y: BigNatural): Int      = x compare y
    override def parseString(str: String): Option[BigNatural] = Some(
      BigNatural(BigDecimal(str))
    )
  }

  implicit def toInt(b: BigNatural): Int = b.toInt
  implicit def toDouble(b: BigNatural): Double = b.toDouble
  implicit def toFloat(b: BigNatural): Float = b.toFloat
  implicit def toLong(b: BigNatural): Long = b.toLong

  implicit class ImplGivenDiscreteDistribution[A](
      distribution: DiscreteDistribution[A]
  ) {
    def is(value: A): DiscreteDistributionValueAssignment[A] =
      DiscreteDistributionValueAssignment(distribution, value)
  }

  implicit class ImplGivenDiscreteConditionalDistribution[A](
      distribution: DiscreteConditionalDistribution[A]
  ) {
    def is(value: A): DiscreteConditionalDistributionValueAssignment[A] =
      DiscreteConditionalDistributionValueAssignment(distribution, value)
  }

  implicit class ImplConditional[A](
      distributionValueAssignment: DiscreteConditionalDistributionValueAssignment[
        A
      ]
  ) {

    /** Syntax for writing a conditional given a value.
      *
      * @param condition
      *   condition value
      * @return
      */
    def |(condition: DistributionValueAssignment[_]): DiscreteConditional[A] =
      DiscreteConditional(distributionValueAssignment, condition)

  }
}
