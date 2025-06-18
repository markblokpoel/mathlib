package mathlib.graph.properties

import mathlib.graph.Node

/** Trait to represent a basic hyper edge.
  * @tparam T
  *   The type of the vertices that belong to this edge.
  */
trait HyperEdge[T <: Node[_]] extends ProtoEdge[T] {

  /** The left side of the hyper edge, namely a list of vertices. */
  val left: Iterable[T]
  /** The right side of the hyper edge, namely a list of vertices. */
  val right: Iterable[T]

  /** Checks if vertex is contained in the hyper edge.
    *
    * @param vertex
    *   The set of vertices to test.
    * @return
    *  `true ` if vertex exists in either the left side or the right side of the hyper edge,
    *  `false ` otherwise.
    */
  def contains(vertex: T): Boolean =
    left.exists(_ == vertex) || right.exists(_ == vertex)

  /** Checks if vertices is contained in the hyper edge.
    *
    * @param vertices
    *   The set of vertices to test.
    * @return
    *  `true ` if vertices is a subset of either the left side or the right side of the hyper
    * edge,  `false ` otherwise.
    */
  def contains(vertices: Set[T]): Boolean =
    vertices.forall(v => left.exists(_ == v)) || vertices.forall(v => right.exists(_ == v))
}
