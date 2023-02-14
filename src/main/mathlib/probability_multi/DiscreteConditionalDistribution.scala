package mathlib.probability_multi
import mathlib.probability_multi.datastructures.{BigNatural, GivenDistribution}
import mathlib.probability_multi.Implicits._

case class DiscreteConditionalDistribution[A](
    id: String,
    domain: Set[A],
    conditions: Seq[Distribution[_]]
) extends Distribution[A] {

  /** @param value
    *   A value within the domain.
    * @return
    *   The probability of the value.
    */
  override def pr(value: A): BigNatural = conditions.foldLeft(0.bigNatural) {
//    (acc: Double, conditional: Distribution[_]) => acc + pr(value | conditional)
    ???
  }

  /** Scales the distribution according to the scalar: pr(domain) * scalar
    *
    * This may de-normalize the distribution.
    *
    * @param scalar
    *   A scalar bigger than 0.
    * @return
    *   The scaled distribution.
    */
  override def *(scalar: BigNatural): Distribution[A] = ???

  override type D = this.type

  override def +(
      other: DiscreteConditionalDistribution.this.type
  ): DiscreteConditionalDistribution.this.type = ???

  override def -(
      other: DiscreteConditionalDistribution.this.type
  ): DiscreteConditionalDistribution.this.type = ???

  /** Inversely scales the distribution according to a scalar: pr(domain) * 1 /
    * scalar = pr(domain) / scalar
    *
    * This may de-normalize the distribution.
    *
    * @param scalar
    *   A scalar bigger than 0.
    * @return
    *   The scaled distribution.
    */
  override def /(scalar: BigNatural): Distribution[A] = ???

  /** Draws a sample from the distribution, proportionate to the probabilities.
    *
    * @return
    *   A sample
    */
  override def sample: A = ???

  /** Returns an iterator containing {{{n}}} samples.
    *
    * @param n
    *   The number of samples to return.
    * @return
    */
  override def sample(n: Int): Iterator[A] = ???

  override def isNormalized: Boolean = ???

  override def error: BigNatural = ???

  /** Returns the value in the domain with the maximum probability. If multiple
    * maxima exist, it returns one of those at random.
    *
    * @return
    *   Most probable value in the domain
    */
  override def argMax: A = ???

  override def exp: Distribution[A] = ???

  override def log: Distribution[A] = ???

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
  override def softmax(beta: BigNatural): Distribution[A] = ???

  /** Returns the Shannon information entropy of this distribution.
    *
    * For distributions that deviate from probability assumptions (i.e., the sum
    * of the values equals 1.0), Shannon information entropy is ill-defined.
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

  override def is(value: A): GivenDistribution = ???
}
