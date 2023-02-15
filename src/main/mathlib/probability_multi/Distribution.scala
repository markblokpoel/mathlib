package mathlib.probability_multi

import mathlib.probability_multi.datastructures.{BigNatural, DistributionValueAssignment}

trait Distribution[A] {

  /** @param value
    *   A value within the domain.
    * @return
    *   The probability of the value.
    */
  @throws[NoSuchElementException]
  def pr(value: A): BigNatural

  /** Scales the distribution according to the scalar: pr(domain) * scalar
    *
    * This may de-normalize the distribution.
    *
    * @param scalar
    *   A scalar bigger than 0.
    * @return
    *   The scaled distribution.
    */
  def *(scalar: BigNatural): Distribution[A]

  type D <: Distribution[A]

  def +(other: D): D

  def -(other: D): D

  def is(value: A): DistributionValueAssignment[A]

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
  @throws[IllegalArgumentException]
  def /(scalar: BigNatural): Distribution[A]

  def isNormalized: Boolean

  def error: BigNatural

  /** Returns the value in the domain with the maximum probability. If multiple
    * maxima exist, it returns one of those at random.
    *
    * @return
    *   Most probable value in the domain
    */
  @throws[IndexOutOfBoundsException]
  def argMax: A

  def exp: Distribution[A]

  def log: Distribution[A]

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
  def softmax(beta: BigNatural): Distribution[A]

  /** Returns the Shannon information entropy of this distribution.
    *
    * For distributions that deviate from probability assumptions (i.e., the sum
    * of the values equals 1.0), Shannon information entropy is ill-defined.
    *
    * @return
    *   Entropy of the distribution
    */
  def entropy: BigNatural

  /** @return
    *   The sum of the probabilities, i.e., the probability mass.
    */
  def sum: BigNatural

  def toString: String

  /** Prints the distribution in a histogram. */
  def hist(): Unit
}
