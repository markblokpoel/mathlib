package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge, WeightedEdge}

case class WUnDiHyperEdge[T <: Node[_]](
    left: Set[T],
    right: Set[T],
    override val weight: Double
) extends HyperEdge[T](left, right)
    with WeightedEdge
    with ProtoEdge[T] {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[WUnDiHyperEdge[T]]

  override def equals(obj: Any): Boolean =
    obj match {
      case that: WUnDiHyperEdge[T] =>
        left == that.left && right == that.right ||
          left == that.right && right == that.left
      case _ => false
    }

  override def hashCode(): Int = {
    val prime = 1693
    (prime + left.hashCode()) * prime + right.hashCode() +
      (prime + right.hashCode()) * prime + left.hashCode()
  }
}
