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
}
