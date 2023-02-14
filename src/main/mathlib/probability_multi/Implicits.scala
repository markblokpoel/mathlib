package mathlib.probability_multi

import mathlib.probability_multi.datastructures.{
  BigNatural,
  GivenDiscreteConditionalDistribution,
  GivenDiscreteDistribution
}

object Implicits {
  implicit class ImplBigNaturalDouble(d: Double) {
    def bigNatural: BigNatural = BigNatural(d)
  }

  implicit class ImplGivenDiscreteDistribution[A](
      distribution: DiscreteDistribution[A]
  ) {
    def is(value: A): GivenDiscreteDistribution[A] =
      GivenDiscreteDistribution(value, distribution)
  }

  implicit class ImplGivenDiscreteConditionalDistribution[A](
      distribution: DiscreteConditionalDistribution[A]
  ) {
    def is(value: A): GivenDiscreteConditionalDistribution[A] =
      GivenDiscreteConditionalDistribution(value, distribution)
  }
}
