package mathlib.probability_multi.datastructures

import mathlib.probability_multi.DiscreteConditionalDistribution

case class GivenDiscreteConditionalDistribution[A](
    value: A,
    distribution: DiscreteConditionalDistribution[A]
) extends GivenDistribution
