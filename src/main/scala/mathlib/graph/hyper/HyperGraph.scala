package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.HyperEdge

import scala.reflect.ClassTag

/** Represents an abstract hyper graph.
  * @param vertices
  *   The vertices in the hyper graph.
  * @param edges
  *   The set of hyper edges in the hyper graph.
  * @tparam T
  *   The type of the vertices.
  * @tparam E
  *   The type of the hyper edges.
  */
abstract class HyperGraph[T, E <: HyperEdge[Node[T]]](
    val vertices: Set[Node[T]],
    val edges: Set[E]
) {

  /** Adds a vertex to the hyper graph.
    * @param vertex
    *   The vertex to be added.
    * @return
    *   An updated hyper graph with the added vertex.
    */
  def +(vertex: Node[T]): HyperGraph[T, E]

  /** Adds a set of vertices to the hyper graph.
    *
    * @param _vertices
    *   The vertices to be added.
    * @return
    *   An updated hyper graph with the added vertices.
    */
  def +(_vertices: Set[Node[T]]): HyperGraph[T, E]

  /** Adds a hyper edge to the hyper graph.
    *
    * This will also add vertices if the hyper edge contains vertices not yet present in the graph.
    *
    * @param edge
    *   The hyper edge to be added.
    * @return
    *   An updated hyper graph with the added hyper edge (and vertices).
    */
  def +(edge: E): HyperGraph[T, E]

  /** Adds a set of hyper edges to the hyper graph.
    *
    * This will also add vertices if any of the hyper edges contains vertices not yet present in the
    * graph.
    *
    * @param _edges
    *   The hyper edges to be added.
    * @tparam X
    *   ClassTag parameter to ensure generic type is not erased.
    * @return
    *   An updated hyper graph with the added hyper edge (and vertices).
    */
  def +[X: ClassTag](_edges: Set[E]): HyperGraph[T, E]

  /** Remove a vertex from the hyper graph.
    *
    * Any hyper edges connected to this vertex will also be removed.
    *
    * @param vertex
    *   The vertex to be removed.
    * @return
    *   The graph minus the vertex and mines hyper edges that were connected.
    */
  def -(vertex: Node[T]): HyperGraph[T, E]

  /** Remove a set of vertices from the graph.
    *
    * Any hyper edges connected to any of the vertices will also be removed. Vertices that are not
    * part of the hyper graph are ignored.
    *
    * @param _vertices
    *   The vertices to be removed.
    * @return
    *   The graph minus the vertices and mines hyper edges that were connected.
    */
  def -(_vertices: Set[Node[T]]): HyperGraph[T, E]

  /** Remove a hyper edge from the graph.
    *
    * If the hyper edge is not part of the graph this function returns the original hyper graph.
    *
    * @param edge
    *   The hyper edge to be removed.
    * @return
    *   The graph minus the hyper edge.
    */
  def -(edge: E): HyperGraph[T, E]

  /** Remove a set of hyper edges from the graph.
    *
    * Hyper edges that are not part of the graph will are ignored.
    * @param _edges
    *   The hyper edges to be removed.
    * @tparam X
    *   ClassTag parameter to ensure generic type is not erased.
    * @return
    *   The graph minus the hyper edges.
    */
  def -[X: ClassTag](_edges: Set[E]): HyperGraph[T, E]

  /** Merge two hyper graphs of the same type.
    *
    * @param that
    *   The hyper graph to be merged with this graph.
    * @tparam G
    *   The type of the hyper graph to be merged (same as this type).
    * @return
    *   The merged hyper graph.
    */
  def merge[G <: HyperGraph[T, E]](that: G): HyperGraph[T, E]

  /** Returns the number of vertices in the hyper graph.
    *
    * @return
    *   The number of vertices in the hyper graph.
    */
  def size: Int = vertices.size

  /** Tests if the hyper graph contains a cycle.
    *
    * @return
    * \```true``` if the graph contains a cycle, ```false``` otherwise.
    */
  def containsCycle: Boolean

  /** Tests if the hyper graph contains any vertices.
    *
    * @return
    * \```true``` if the hyper graph contains no vertices, ```false``` otherwise.
    */
  def isEmpty: Boolean = vertices.isEmpty

  /** Tests if the hyper graph contains any edges.
    *
    * @return
    * \```true``` if the hyper graph no edges, ```false``` otherwise.
    */
  def noEdges: Boolean = edges.isEmpty
}
