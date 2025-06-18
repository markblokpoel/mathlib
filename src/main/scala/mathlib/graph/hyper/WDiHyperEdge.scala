package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge, WeightedEdge}

/** Represents a weighted directed hyper edge.
  * @param left
  *   The left side of the hyper edge, namely a list of vertices.
  * @param right
  *   The right side of the hyper edge, namely a list of vertices.
  * @param weight
  *   The weight of the edge.
  * @tparam T
  *   The type of the vertices that belong to this edge.
  */
case class WDiHyperEdge[T <: Node[_]](
    override val left: Set[T],
    override val right: Set[T],
    override val weight: Double
) extends HyperEdge[T]
    with WeightedEdge {

  /** Checks if this instance can equal that instance.
    * @param that
    *   The instance to check.
    * @return
    *  `true ` if  `that ` is of type [[WDiHyperEdge]]
    */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[WDiHyperEdge[_]]

  /** Checks if this equals that.
    * @param that
    *   The object to test equivalence to.
    * @return
    *  `true ` if that.left == that.left && that.right == that.right, ignoring the weight of the
    * hyper edges.
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
