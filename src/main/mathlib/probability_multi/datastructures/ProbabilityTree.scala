package mathlib.probability_multi.datastructures

import mathlib.probability_multi.{DiscreteDistribution, Distribution}

/**
 * Constructs a tree-representation of the distribution where elements are organized according to their
 * probability mass.
 * @param distribution The distribution values for which to construct the probability tree.
 * @tparam A The type of the values of the distribution.
 */
case class ProbabilityTree[A](distribution: DiscreteDistribution[A]) {
  private val values = distribution.distribution.keys.toVector
  private val probabilities = values.map(distribution.distribution(_))
  val tree: PTree[A] = constructPTree(values, probabilities, BigNatural(0))

  /**
   * Retrieves the element from the distribution at mass-point {{{p}}}.
   * @param p The probability.
   * @return
   */
  def apply(p: BigNatural): A = tree(p)

  private def constructPTree(values: Vector[A], probabilities: Vector[BigNatural], p: BigNatural): PTree[A] = {
    val (leftValues, rightValues) = values.splitAt(values.length/2)
    val (leftProbabilities, rightProbabilities) = probabilities.splitAt(probabilities.length/2)
    val leftSum = leftProbabilities.fold(BigNatural(0))((acc: BigNatural, p: BigNatural) => acc + p)

    if(values.tail.isEmpty) PLeaf(values.head)
    else {
      PBranch(constructPTree(leftValues, leftProbabilities, BigNatural(0)),
        constructPTree(rightValues, rightProbabilities, leftSum + p),
        leftSum + p)
    }
  }
}
