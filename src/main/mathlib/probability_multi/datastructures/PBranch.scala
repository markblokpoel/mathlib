package mathlib.probability_multi.datastructures

class PBranch[A](val left: PTree[A], val right: PTree[A], val threshold: BigNatural) extends PTree[A] {
  def apply(t: BigNatural): A =
    if (t <= threshold) left(t)
    else right(t)

  override def toString: String =
    "<=" + threshold + "(" + left + ", " + right + ")"
}

object PBranch {
  def apply[A](left: PTree[A], right: PTree[A], threshold: BigNatural): PBranch[A] =
    new PBranch(left, right, threshold)
}