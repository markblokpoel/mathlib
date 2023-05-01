package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge, WeightedEdge}

case class WUnDiEdge[T <: Node[_]](override val left:T, override val right: T, weight: Double) extends Edge[T](left, right) with WeightedEdge with ProtoEdge[T] {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[UnDiEdge[T]]

  override def equals(obj: Any): Boolean =
    obj match {
      case that: UnDiEdge[T] =>
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
