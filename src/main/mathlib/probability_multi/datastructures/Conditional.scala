package mathlib.probability_multi.datastructures

import mathlib.probability_multi.DiscreteConditionalDistribution

trait Conditional

case class DiscreteConditionalValue[A](
    value: DiscreteConditionalDistributionValueAssignment[A],
    conditionals: DistributionValueAssignment[_]*
) extends Conditional {
  override def toString: String = {
    value.toString + " | " + conditionals.mkString(" ")
  }
}

case class DiscreteConditionalNoAssignment[A](
    distribution: DiscreteConditionalDistribution[A],
    conditionals: DistributionValueAssignment[_]*
) extends Conditional {
  override def toString: String = {
    distribution.id + " | " + conditionals.mkString(" ")
  }
}
