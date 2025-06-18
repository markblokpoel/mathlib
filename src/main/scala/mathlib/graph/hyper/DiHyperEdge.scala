package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge}

/** Represents a directed hyper edge.
  * @param left
  *   The left side of the hyper edge, namely a list of vertices.
  * @param right
  *   The right side of the hyper edge, namely a list of vertices.
  * @tparam T
  *   The type of the vertices that belong to this edge.
  */
case class DiHyperEdge[T <: Node[_]](override val left: Set[T], override val right: Set[T])
    extends HyperEdge[T]
    with ProtoEdge[T] {

  /** Checks if this instance can equal that instance.
    * @param that
    *   The instance to check.
    * @return
    *  `true ` if  `that ` is of type [[DiHyperEdge]]
    */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[DiHyperEdge[_]]

  /** Checks if this equals that.
    * @param that
    *   The object to test equivalence to.
    * @return
    *  `true ` if that.left == that.left && that.right == that.right.
    */
  override def equals(that: Any): Boolean =
    that match {
      case that: DiHyperEdge[_] =>
        left == that.left && right == that.right
      case _ => false
    }

  /** Calculates the hash code of the hyper edge.
    * @return
    *   Hash code of the edge.
    */
  override def hashCode(): Int = {
    val prime = 1693
    (prime + left.hashCode()) * prime + right.hashCode()
  }
}
