package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge}

case class UnDiHyperEdge[T <: Node[_]](left: Set[T], right: Set[T]) extends HyperEdge[T](left, right) with ProtoEdge[T] {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[UnDiHyperEdge[T]]

  override def equals(obj: Any): Boolean =
    obj match {
      case that: UnDiHyperEdge[T] =>
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
