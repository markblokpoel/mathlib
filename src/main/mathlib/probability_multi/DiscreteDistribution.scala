package mathlib.probability_multi

import mathlib.probability_multi.Implicits._
import mathlib.probability_multi.datastructures.{
  BigNatural,
  Conditional,
  DiscreteDistributionValueAssignment,
  DiscreteDomain,
  DistributionValueAssignment,
  ProbabilityTree
}

import scala.reflect.ClassTag
import scala.util.Random

/** Implements a generic probability distribution. The probabilities are represented by Double for
  * increased accuracy.
  *
  * @param domain
  *   The set of values that span this distribution.
  * @param distribution
  *   The probabilities for each value in the domain.
  * @tparam A
  *   The type of the domain.
  */
case class DiscreteDistribution[A](
    id: String,
    domain: Set[A],
    distribution: Map[A, BigNatural]
) extends Distribution[A] {

  override type D = DiscreteDistribution[A]
  override type DVA1 = DiscreteDistributionValueAssignment[A]
  override type DVA2 = DiscreteDistributionValueAssignment[A]

  /** @param valueAssignment
   * A value within the domain.
   * @return
   * The probability of the value.
   */
  override def pr(valueAssignment: DiscreteDistributionValueAssignment[A]): BigNatural = {
    val seq: Seq[BigNatural] =
      valueAssignment.values.map(value => distribution(value))
    seq.sum
  }

  override def is(value: A): DiscreteDistributionValueAssignment[A] =
    DiscreteDistributionValueAssignment(this, value)

  override def allValueAssignments: Set[DiscreteDistributionValueAssignment[A]] =
    domain.map(value => DiscreteDistributionValueAssignment(distribution = this, value))

  /** Scales the distribution according to the scalar: pr(domain) * scalar
    *
    * This may de-normalize the distribution.
    *
    * @param scalar
    *   The scalar value with which to multiply the distribution.
    * @return
    *   The scaled distribution.
    */
  override def *(scalar: BigNatural): DiscreteDistribution[A] =
    DiscreteDistribution(
      id,
      domain,
      distribution.view.mapValues(_ * scalar).toMap
    )

  override def +(other: D): D = {
    require(
      domain == other.domain,
      "addition for distributions requires the same domains"
    )

    DiscreteDistribution(
      id,
      domain,
      domain
        .map(key => (key, distribution(key) + other.distribution(key)))
        .toMap
    )
  }

  override def -(other: D): D = {
    require(
      domain == other.domain,
      "subtraction for distributions requires the same domains"
    )

    DiscreteDistribution(
      id,
      domain,
      domain
        .map(key => (key, distribution(key) - other.distribution(key)))
        .toMap
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
  @throws[IllegalArgumentException]
  override def /(scalar: BigNatural): DiscreteDistribution[A] = {
    require(scalar != 0, "Cannot divide by 0.")
    DiscreteDistribution(
      id,
      domain,
      distribution.view.mapValues(_ / scalar).toMap
    )
  }

  /** Efficient representation for sampling. Compute when needed and only once.
    */
  lazy private val pTree = ProbabilityTree(this)

  /** Draws a sample from the distribution, proportionate to the probabilities.
    *
    * @return
    *   A sample
    */
  def sample: A = pTree(BigNatural(Random.nextDouble()) * sum)

  /** Returns an iterator containing {{{n}}} samples.
    *
    * @param n
    *   The number of samples to return.
    * @return
    */
  def sample(n: Int): Iterator[A] = new Iterator[A]() {
    private var sampleCount = 0

    override def hasNext: Boolean = sampleCount < n

    override def next(): A = {
      sampleCount += 1
      sample
    }
  }

  override def isNormalized: Boolean = sum == 1

  override def error: BigNatural = BigNatural(1.0) - sum

  /** Returns the value in the domain with the maximum probability. If multiple maxima exist, it
    * returns one of those at random.
    *
    * @return
    *   Most probable value in the domain
    */
  @throws[IndexOutOfBoundsException]
  override def argMax: A = {
    val maxValue  = distribution.values.max
    val maxValSet = distribution.filter(_._2 == maxValue).keys.toList
    // If one or more maxima exist return random
    maxValSet(Random.nextInt(maxValSet.length))
  }

  override def exp: DiscreteDistribution[A] =
    DiscreteDistribution(id, domain, distribution.view.mapValues(_.exp).toMap)

  override def log: DiscreteDistribution[A] =
    DiscreteDistribution(id, domain, distribution.view.mapValues(_.log).toMap)

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
  override def softmax(beta: BigNatural): DiscreteDistribution[A] =
    (this.log * beta).exp / (this.log * beta).exp.sum

  /** Returns the Shannon information entropy of this distribution.
    *
    * For distributions that deviate from probability assumptions (i.e., the sum of the values
    * equals 1.0), Shannon information entropy is ill-defined.
    *
    * @return
    *   Entropy of the distribution
    */
  override def entropy: BigNatural = {
    distribution.values.foldLeft(BigNatural(0.0))((e: BigNatural, p: BigNatural) =>
      e - (if (p > 0) p * p.log / BigNatural(2).log else BigNatural(0.0))
    )
  }

  /** @return
    *   The sum of the probabilities, i.e., the probability mass.
    */
  override def sum: BigNatural =
    distribution.values.fold(BigNatural(0.0))((acc: BigNatural, p: BigNatural) => acc + p)

  override def toString: String = distribution.mkString("{", ", ", "}")

  /** Prints the distribution in a histogram. */
  override def hist(): Unit = {
    val maxStrLen = domain.map(_.toString.length).max

    println("** " + id + " **")
    domain.foreach(value => {
      val p: BigNatural = distribution(value)
      val len: Int      = 20 * p
      val hs            = List.tabulate(len)(_ => "#").mkString
      println(
        value.toString +
          " " * (maxStrLen - value.toString.length + 1) +
          f"${p.doubleValue()}%1.4f\t$hs"
      )
    })
  }


}

/** Factory for [[Distribution]] instances. */
case object DiscreteDistribution {
  def apply[A, X: ClassTag](
      id: String,
      domain: Vector[A],
      distribution: Vector[BigNatural]
  ): DiscreteDistribution[A] =
    new DiscreteDistribution(id, domain.toSet, (domain zip distribution).toMap)

  def apply[A, X: ClassTag, Y: ClassTag](
      id: String,
      domain: Vector[A],
      distribution: Vector[BigNatural]
  ): DiscreteDistribution[A] =
    new DiscreteDistribution(id, domain.toSet, (domain zip distribution).toMap)
}
