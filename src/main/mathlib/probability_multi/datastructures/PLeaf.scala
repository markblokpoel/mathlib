package mathlib.probability_multi.datastructures

class PLeaf[A](val value: A) extends PTree[A] {
  def apply(t: BigNatural): A = value

  override def toString: String = value.toString
}

object PLeaf {
  def apply[A](value: A): PLeaf[A] = new PLeaf(value)

}