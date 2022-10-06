package mathlib.probability.datastructures

import mathlib.probability.Distribution

/**
 * Constructs a tree-representation of the distribution where elements are organized according to their
 * probability mass.
 * @param distribution
 * @tparam A
 */
case class ProbabilityTree[A](distribution: Distribution[A]) {
  private val values = distribution.distribution.keys.toVector
  private val probabilities = values.map(distribution.distribution(_))
  val tree: PTree[A] = constructPTree(values, probabilities, 0.0)

  /**
   * Retrieves the element from the distribution at mass-point {{{p}}}.
   * @param p
   * @return
   */
  def apply(p: Double): A = tree(p)

  private def constructPTree(values: Vector[A], probabilities: Vector[Double], p: Double): PTree[A] = {
    val (leftValues, rightValues) = values.splitAt(values.length/2)
    val (leftProbabilities, rightProbabilities) = probabilities.splitAt(probabilities.length/2)
    val leftSum = leftProbabilities.fold(0.0)((acc: Double, p: Double) => acc + p)

    if(values.tail.isEmpty) PLeaf(values.head)
    else {
      PBranch(constructPTree(leftValues, leftProbabilities, 0.0),
        constructPTree(rightValues, rightProbabilities, leftSum + p),
        leftSum + p)
    }
  }
}
