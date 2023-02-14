package mathlib.probability_multi.datastructures

trait Conditional

case class DiscreteConditional[A](
    value: DistributionValueAssignment[A],
    conditionals: DistributionValueAssignment[_]*
) {
  override def toString: String = {
    value.toString + " | " + conditionals.mkString(" ")
  }
}
