package mathlib.graph.properties

import mathlib.graph.{Graph, Node}

/** Represents an abstract edge data structure.
  * @param left
  *   Left vertex of the edge.
  * @param right
  *   Right vertex of the edge.
  * @tparam T
  *   The type of the vertices.
  */
abstract class Edge[T <: Node[_]](val left: T, val right: T) extends ProtoEdge[T] {

  /** Checks if the edge contains the given vertex.
    * @param vertex
    *   The vertex to check.
    * @return
    *   ```true``` if the vertex is part of this edge, ```false``` otherwise.
    */
  def contains(vertex: T): Boolean = left == vertex || right == vertex

  /** Get the neighboring vertex of the base vertex.
    *
    * Uses [[scala.Option]] as the return type for compatibility with edges that may not have a neighbor.
    * @param vertex
    *   The base vertex.
    * @return
    *   The neighbor of vertex, wrapped in ```Some(.)```.
    */
  def getNeighborOf(vertex: T): Option[T] =
    if (left == vertex) Some(right)
    else Some(left)
}
