package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge}

/** Represents a directed edge.
  * @param left
  *   Left vertex of the edge.
  * @param right
  *   Right vertex of the edge.
  * @tparam T
  *   The type of the vertices.
  */
case class DiEdge[T <: Node[_]](override val left: T, override val right: T)
    extends Edge[T](left, right)
    with ProtoEdge[T] {

  /** Gets the neighbor of vertex, respecting directionality.
    * @param vertex
    *   The base vertex.
    * @return
    *   The neighbor of vertex wrapped in ```Some(.)``` if vertex is on the left side of the edge,
    *   or ```None``` otherwise.
    */
  override def getNeighborOf(vertex: T): Option[T] =
    if (left == vertex) Some(right)
    else None

  /** Checks if this instance can equal that instance.
   *
   * @param that
   * The instance to check.
   * @return
   * ```true``` if ```that``` is of type [[DiEdge]]
   */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[DiEdge[_]]

  /** Checks if this equals that.
   * @param that
   * The object to test equivalence to.
   * @return
   * ```true``` if that.left == that.left && that.right == that.right.
   */
  override def equals(that: Any): Boolean =
    that match {
      case that: DiEdge[_] =>
        left == that.left && right == that.right
      case _ => false
    }

  /** Calculates the hash code of the edge.
   * @return
   * Hash code of the edge.
   */
  override def hashCode(): Int = {
    val prime = 1693
    (prime + left.hashCode()) * prime + right.hashCode()
  }
}
