package mathlib.probability


/** Helpers for [[Distribution]] and [[ConditionalDistribution]]. */
object DistributionHelpers {
  def pr[A](value: A, distribution: Distribution[A]): Double = distribution.pr(value)

  def pr[A, B](conditional: Conditional1[A, B], distribution: ConditionalDistribution[A, B]): Double =
    distribution.pr(conditional)

  def prValue[A, B](value: A, distribution: ConditionalDistribution[A, B]): Double = distribution.prValue(value)

  def pr[A, B](given: B, distribution: ConditionalDistribution[A, B]): Distribution[A] = distribution.pr(given)

  def exp[A](distribution: Distribution[A]): Distribution[A] = distribution.exp

  def log[A](distribution: Distribution[A]): Distribution[A] = distribution.log

  def exp[A, B](distribution: ConditionalDistribution[A, B]): ConditionalDistribution[A, B] = distribution.exp

  def log[A, B](distribution: ConditionalDistribution[A, B]): ConditionalDistribution[A, B] = distribution.log

}
