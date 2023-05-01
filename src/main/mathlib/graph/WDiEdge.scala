package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge, WeightedEdge}

case class WDiEdge[T <: Node[_]](override val left: T, override val right: T, weight: Double)
    extends Edge[T](left, right)
    with WeightedEdge
    with ProtoEdge[T] {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[WDiEdge[T]]

  override def equals(obj: Any): Boolean =
    obj match {
      case that: WDiEdge[T] =>
        left == that.left && right == that.right
      case _ => false
    }

  override def hashCode(): Int = {
    val prime = 1693
    (prime + left.hashCode()) * prime + right.hashCode()
  }
}
