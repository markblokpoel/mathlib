package mathlib.probability

import mathlib.probability.datastructures.ProbabilityTree
import scala.reflect.ClassTag
import scala.util.Random

/**
 * Implements a generic probability distribution. The probabilities are represented by Double for increased
 * accuracy.
 *
 * @param domain       The set of values that span this distribution.
 * @param distribution The probabilities for each value in the domain.
 * @tparam A The type of the domain.
 */
case class Distribution[A](domain: Set[A], distribution: Map[A, Double]) {

  /**
   * @param value A value within the domain.
   * @return The probability of the value.
   */
  @throws[NoSuchElementException]
  def pr(value: A): Double = distribution(value)

  /**
   * Scales the distribution according to the scalar: pr(domain) * scalar
   *
   * This may de-normalize the distribution.
   *
   * @param scalar
   * @return The scaled distribution.
   */
  def *(scalar: Double): Distribution[A] = Distribution(domain, distribution.view.mapValues(_ * scalar).toMap)

  def +(other: Distribution[A]): Distribution[A] = {
    require(domain == other.domain, "addition for distributions requires the same domains")

    Distribution(domain, domain.map(key => (key, distribution(key) + other.distribution(key))).toMap)
  }

  def -(other: Distribution[A]): Distribution[A] = {
    require(domain == other.domain, "subtraction for distributions requires the same domains")

    Distribution(domain, domain.map(key => (key, distribution(key) - other.distribution(key))).toMap)
  }

  /**
   * Inversely scales the distribution according to a scalar: pr(domain) * 1 / scalar = pr(domain) / scalar
   *
   * This may de-normalize the distribution.
   *
   * @param scalar A scalar bigger than 0.
   * @return The scaled distribution.
   */
  @throws[IllegalArgumentException]
  def /(scalar: Double): Distribution[A] = {
    require(scalar != 0, "Cannot divide by 0.")
    Distribution(domain, distribution.view.mapValues(_ / scalar).toMap)
  }

  /** Efficient representation for sampling. Compute when needed and only once. */
  lazy private val pTree = ProbabilityTree(this)

  /**
   * Draws a sample from the distribution, proportionate to the probabilities.
   *
   * @return A sample
   */
  def sample: A = pTree(Random.nextDouble() * sum)

  /**
   * Returns an iterator containing {{{n}}} samples.
   *
   * @param n
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

  def isNormalized: Boolean = sum == 1

  def accuracy: Double = 1.0.toDouble - sum

  /** Returns the value in the domain with the maximum probability.
   * If multiple maxima exist, it returns one of those at random.
   *
   * @return Most probable value in the domain
   */
  @throws[IndexOutOfBoundsException]
  def argMax: A = {
    val maxValue = distribution.values.max
    val maxValSet = distribution.filter(_._2 == maxValue).keys.toList
    // If one or more maxima exist return random
    maxValSet(Random.nextInt(maxValSet.length))
  }

  def exp: Distribution[A] = Distribution(domain, distribution.view.mapValues(math.exp).toMap)

  def log: Distribution[A] = Distribution(domain, distribution.view.mapValues(math.log).toMap)

  /**
   * Returns the softmaxed distribution
   *
   * @param beta The beta parameter
   * @return A value in the domain of distribution
   * @see See this Wikipedia page for a mathmatical definition of soft argmax
   *      [[https://en.wikipedia.org/wiki/Softmax_function]].
   */
  def softmax(beta: Double): Distribution[A] = (this.log * beta).exp / (this.log * beta).exp.sum

  /** Returns the Shannon information entropy of this distribution.
   *
   * For distributions that deviate from probability assumptions (i.e., the sum of the values
   * equals 1.0), Shannon information entropy is ill-defined.
   *
   * @return Entropy of the distribution
   */
  def entropy: Double = {
    distribution.values.foldLeft(0.0)((e: Double, p: Double) =>
      e - (if(p > 0) p * math.log(p) / math.log(2) else 0.0)
    )
  }

  /**
   * @return The sum of the probabilities, i.e., the probability mass.
   */
  def sum: Double = distribution.values.fold(0.0)((acc: Double, p: Double) => acc + p)

  override def toString: String = distribution.mkString("{", ", ", "}")

  /** Prints the distribution in a histogram. */
  def hist(): Unit = {
    val maxStrLen = domain.map(_.toString.length).max

    domain.foreach(value => {
      val p = distribution(value)
      val hs = List.tabulate((20 * p).intValue())(_ => "#").mkString
      println(
        value.toString +
          " " * (maxStrLen - value.toString.length + 1) +
          f"${p.doubleValue()}%1.4f\t$hs")
    }
    )
  }
}

/** Factory for [[Distribution]] instances. */
case object Distribution {
  def apply[A, X: ClassTag](domain: Vector[A], distribution: Vector[Double]): Distribution[A] =
    Distribution(domain.toSet, (domain zip distribution).toMap)

  def apply[A, X: ClassTag, Y: ClassTag](domain: Vector[A], distribution: Vector[Double]): Distribution[A] =
    Distribution(domain.toSet, (domain zip distribution).toMap)
}