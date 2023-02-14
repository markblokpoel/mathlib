package mathlib.probability_multi.datastructures

import mathlib.probability_multi.DiscreteDistribution

case class GivenDiscreteDistribution[A](
    value: A,
    distribution: DiscreteDistribution[A]
)
