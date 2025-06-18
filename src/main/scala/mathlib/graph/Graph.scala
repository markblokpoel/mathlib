package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag

/** This trait represent the base of all graphs (excluding hyper graphs).
  *
  * For any graph, each vertex in every edge must be in the set of vertices, otherwise an
  * exception will be thrown.

  * @tparam T
  *   The type of nodes in the graph.
  * @tparam E
  *   The type of edges of the graph, must be a subtype of [[properties.Edge]].
  */
trait Graph[T, E <: Edge[Node[T]]] {
  /** The vertices in the graph. */
  val vertices: Set[Node[T]];
  /** The edges in the graph. */
  val edges: Set[E];

  require(
    edges.forall(e => vertices.contains(e.left) && vertices.contains(e.right)),
    "Cannot form graph, the following edges contain vertices not passed to the constructor: "
      + edges.filter(e => (vertices contains e.left) || (vertices contains e.right))
  )

  /** Add a vertex to the graph.
    * @param vertex
    *   The vertex to be added.
    * @return
    *   The graph with the added vertex.
    */
  def +(vertex: Node[T]): Graph[T, E]

  /** Add a set of vertices to the graph.
    * @param _vertices
    *   The vertices to be added.
    * @return
    *   The graph with the added vertices.
    */
  def +(_vertices: Set[Node[T]]): Graph[T, E]

  /** Add an edge to the graph.
    *
    * This will also add vertices if the edge contains vertices not yet present in the graph.
    * @param edge
    *   The edge to be added.
    * @return
    *   The graph with the added edge (and vertices).
    */
  def +(edge: E): Graph[T, E]

  /** Add a set of edges to the graph.
    *
    * This will also add vertices if any of the edges contains vertices not yet present in the
    * graph.
    * @param _edges
    *   The set of edges to be added.
    * @tparam X
    *   ClassTag parameter to ensure generic type is not erased.
    * @return
    *   The graph with the added edges (and vertices).
    */
  def +[X: ClassTag](_edges: Set[E]): Graph[T, E]

  /** Remove a vertex from the graph.
    *
    * Any edges connected to this vertex will also be removed. If the vertex is not part of the
    * graph, this function returns the original graph.
    * @param vertex
    *   The vertex to be removed.
    * @return
    *   The graph minus the vertex and mines edges that were connected.
    */
  def -(vertex: Node[T]): Graph[T, E]

  /** Remove a set of vertices from the graph.
    *
    * Any edges connected to any of the vertices will also be removed. Vertices that are not part of
    * the graph are ignored.
    *
    * @param _vertices
    *   The vertices to be removed.
    * @return
    *   The graph minus the vertices and mines edges that were connected.
    */
  def -(_vertices: Set[Node[T]]): Graph[T, E]

  /** Remove an edge from the graph.
    *
    * If the edge is not part of the graph this function does nothing.
    *
    * @param edge
    *   The edge to be removed.
    * @return
    *   The graph minus the edge.
    */
  def -(edge: E): Graph[T, E]

  /** Remove a set of edges from the graph.
    *
    * Edges that are not part of the graph will are ignored.
    *
    * @param _edges
    *   The edges to be removed.
    * @tparam X
    *   ClassTag parameter to ensure generic type is not erased.
    * @return
    *   The graph minus the edges.
    */
  def -[X: ClassTag](_edges: Set[E]): Graph[T, E]

  /** Merge two graphs of the same type.
    * @param that
    *   The graph to be merged with this graph.
    * @tparam G
    *   The type of the graph to be merged (same as this type).
    * @return
    *   The merged graph.
    */
  def merge[G <: Graph[T, E]](that: G): Graph[T, E]

  /** Returns the number of vertices in the graph.
    * @return
    *   The number of vertices in the graph.
    */
  def size: Int = vertices.size

  /** Tests if the graph contains a cycle.
    * @return
    *    `true ` if the graph contains a cycle,  `false ` otherwise.
    */
  def containsCycle: Boolean

  /** Tests if the graph contains any vertices.
    * @return
    *    `true ` if the graph contains no vertices,  `false ` otherwise.
    */
  def isEmpty: Boolean = vertices.isEmpty

  /** Tests if the graph contains any edges.
    * @return
    *    `true ` if the graph no edges,  `false ` otherwise.
    */
  def noEdges: Boolean = edges.isEmpty

  /** Returns the degree of a vertex in the graph.
   * @param vertex The vertex.
   * @return The degree of vertex.
   */
  def degreeOf(vertex: Node[T]): Int

  /** Returns a DOT string for visualization.
   *
   * @return A DOT string.
   */
  def toDOTString: String
}
