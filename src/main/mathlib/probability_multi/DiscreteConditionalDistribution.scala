package mathlib.probability_multi
import mathlib.probability_multi.datastructures.{
  BigNatural,
  DiscreteConditionalDistributionValueAssignment,
  DiscreteConditionalNoAssignment,
  DiscreteConditionalValue,
  DiscreteDistributionValueAssignment,
  DistributionValueAssignment
}
import mathlib.probability_multi.Implicits._

case class DiscreteConditionalDistribution[A](
    id: String,
    domain: Set[A],
    distribution: Map[(A, Seq[DistributionValueAssignment[_]]), BigNatural],
    conditions: Distribution[_]*
) extends Distribution[A] {

  override type D    = DiscreteConditionalDistribution[A]
  override type DVA1 = DiscreteDistributionValueAssignment[A]
  override type DVA2 = DiscreteConditionalDistributionValueAssignment[A]

  /** @param valueAssignment
    *   A value within the domain.
    * @return
    *   The probability of the value.
    */
  override def pr(valueAssignment: DiscreteConditionalDistributionValueAssignment[A]): BigNatural = {
//    val seq: Seq[BigNatural] =
//      valueAssignment.values.map(value => distribution(value))
//    seq.sum

    //    conditions.foldLeft(BigNatural(0))(
//      (acc: Double, conditional: Distribution[_]) => acc + pr(value | conditional)

//    )
    ???
  }

  def pr(
      conditionalValue: DistributionValueAssignment[_]
  ): DiscreteConditionalDistribution[A] = {


    ???
  }

  def pr(valueWithConditionalValues: DiscreteConditionalValue[A]): BigNatural = {
    val values = valueWithConditionalValues.value
    val conditionalValues: Seq[DistributionValueAssignment[_]] =
      valueWithConditionalValues.conditionals

//    values.values
//      .map(v => {})
//      .sum
    ???
  }

  def pr(noValueWithConditionalValues: DiscreteConditionalNoAssignment[A]): BigNatural = ???

  override def is(value: A): DiscreteConditionalDistributionValueAssignment[A] =
    DiscreteConditionalDistributionValueAssignment(this, value)

  def |(
      conditionalValueAssignments: DistributionValueAssignment[_]*
  ): DiscreteConditionalNoAssignment[A] =
    DiscreteConditionalNoAssignment(this, conditionalValueAssignments: _*)

  override def allValueAssignments: Set[DiscreteConditionalDistributionValueAssignment[A]] =
    domain.map(value => DiscreteConditionalDistributionValueAssignment(distribution = this, value))

  /** Scales the distribution according to the scalar: pr(domain) * scalar
    *
    * This may de-normalize the distribution.
    *
    * @param scalar
    *   A scalar bigger than 0.
    * @return
    *   The scaled distribution.
    */
  override def *(scalar: BigNatural): DiscreteConditionalDistribution[A] =
    DiscreteConditionalDistribution(
      id,
      domain,
      distribution.view.mapValues(_ * scalar).toMap,
      conditions: _*
    )

  override def +(other: DiscreteConditionalDistribution[A]): DiscreteConditionalDistribution[A] = {
    require(
      domain == other.domain,
      "addition for conditional distributions requires the same domains"
    )
    require(
      conditions == other.conditions,
      "addition for conditional distributions requires the same conditional variables"
    )
    DiscreteConditionalDistribution(
      id,
      domain,
      distribution.keySet
        .map(key => {
          key -> (distribution(key) + other.distribution(key))
        })
        .toMap,
      conditions: _*
    )
  }

  override def -(other: DiscreteConditionalDistribution[A]): DiscreteConditionalDistribution[A] = {
    require(
      domain == other.domain,
      "subtraction for conditional distributions requires the same domains"
    )
    require(
      conditions == other.conditions,
      "subtraction for conditional distributions requires the same conditional variables"
    )
    DiscreteConditionalDistribution(
      id,
      domain,
      distribution.keySet
        .map(key => {
          key -> (distribution(key) - other.distribution(key))
        })
        .toMap,
      conditions: _*
    )
  }

  /** Inversely scales the distribution according to a scalar: pr(domain) * 1 / scalar = pr(domain)
    * / scalar
    *
    * This may de-normalize the distribution.
    *
    * @param scalar
    *   A scalar bigger than 0.
    * @return
    *   The scaled distribution.
    */
  override def /(scalar: BigNatural): DiscreteConditionalDistribution[A] =
    DiscreteConditionalDistribution(
      id,
      domain,
      distribution.view.mapValues(_ / scalar).toMap,
      conditions: _*
    )

  override def isNormalized: Boolean = ???

  override def error: BigNatural = ???

  /** Returns the value in the domain with the maximum probability. If multiple maxima exist, it
    * returns one of those at random.
    *
    * @return
    *   Most probable value in the domain
    */
  override def argMax: A = ???

  override def exp: D = ???

  override def log: D = ???

  /** Returns the softmaxed distribution
    *
    * @param beta
    *   The beta parameter
    * @return
    *   A value in the domain of distribution
    * @see
    *   See this Wikipedia page for a mathmatical definition of soft argmax
    *   [[https://en.wikipedia.org/wiki/Softmax_function]].
    */
  override def softmax(beta: BigNatural): D = ???

  /** Returns the Shannon information entropy of this distribution.
    *
    * For distributions that deviate from probability assumptions (i.e., the sum of the values
    * equals 1.0), Shannon information entropy is ill-defined.
    *
    * @return
    *   Entropy of the distribution
    */
  override def entropy: BigNatural = ???

  /** @return
    *   The sum of the probabilities, i.e., the probability mass.
    */
  override def sum: BigNatural = ???

  /** Prints the distribution in a histogram. */
  override def hist(): Unit = ???

}
