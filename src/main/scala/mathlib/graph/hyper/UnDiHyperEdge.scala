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
case class UnDiHyperEdge[T <: Node[_]](left: Set[T], right: Set[T]) extends HyperEdge[T](left, right) with ProtoEdge[T] {
  /** Checks if this instance can equal that instance.
   *
   * @param that
   * The instance to check.
   * @return
   * ```true``` if ```that``` is of type [[UnDiHyperEdge]]
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[UnDiHyperEdge[_]]

  /** Checks if this equals that.
   *
   * Undirected hyper edges (x,y) equal (y,x). This equals function tests equivalence respecting this
   * property.
   * @param that
   * The object to test equivalence to.
   * @return
   * ```true``` if that.left == that.left && that.right == that.right or that.left == that.right
   * && that.right == that.left.
   */
  override def equals(that: Any): Boolean =
    that match {
      case that: UnDiHyperEdge[_] =>
        left == that.left && right == that.right ||
          left == that.right && right == that.left
      case _ => false
    }

  /** Calculates the hash code of the hyper edge.
   * @return
   * Hash code of the edge.
   */
  override def hashCode(): Int = {
    val prime = 1693
    (prime + left.hashCode()) * prime + right.hashCode() +
      (prime + right.hashCode()) * prime + left.hashCode()
  }
}
