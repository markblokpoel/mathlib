package mathlib.probability_multi.datastructures

trait Conditional

case class DiscreteConditional[A](
    value: DiscreteConditionalDistributionValueAssignment[A],
    conditionals: DistributionValueAssignment[_]*
) extends Conditional {
  override def toString: String = {
    value.toString + " | " + conditionals.mkString(" ")
  }
}
