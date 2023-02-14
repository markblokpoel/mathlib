package mathlib.probability_multi

import mathlib.probability_multi.datastructures.{
  BigNatural,
  DiscreteConditional,
  DiscreteConditionalDistributionValueAssignment,
  DiscreteDistributionValueAssignment,
  DistributionValueAssignment
}

object Implicits {
  implicit class ImplBigNaturalDouble(d: Double) {
    def bigNatural: BigNatural = BigNatural(d)
  }

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
