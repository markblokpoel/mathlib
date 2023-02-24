package mathlib.probability_multi.datastructures

import mathlib.probability_multi.{
  DiscreteConditionalDistribution,
  DiscreteDistribution
}

trait DistributionValueAssignment[A] {
  def or(value2: A): DistributionValueAssignment[A]
}

case class DiscreteDistributionValueAssignment[A](
    distribution: DiscreteDistribution[A],
    values: A*
) extends DistributionValueAssignment[A] {

  override def toString: String = {
    distribution.id + " = " + values.reverse.mkString(" or ")
  }

  override def or(value2: A): DiscreteDistributionValueAssignment[A] = DiscreteDistributionValueAssignment(
    distribution,
    value2 +: values: _*
  )
}

case class DiscreteConditionalDistributionValueAssignment[A](
    distribution: DiscreteConditionalDistribution[A],
    values: A*
) extends DistributionValueAssignment[A] {

  override def toString: String = {
    distribution.id + " = " + values.reverse.mkString(" or ")
  }

  override def or(value2: A): DiscreteConditionalDistributionValueAssignment[A] =
    DiscreteConditionalDistributionValueAssignment(
      distribution,
      value2 +: values: _*
    )

  def |(conditionalValueAssignments: DistributionValueAssignment[_]*): DiscreteConditional[A] =
    DiscreteConditional(this, conditionalValueAssignments:_*)
}
