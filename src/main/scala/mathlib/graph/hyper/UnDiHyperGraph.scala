package mathlib.graph.hyper

import mathlib.graph.GraphImplicits.N
import mathlib.graph.Node

import scala.reflect.ClassTag

/** Represents an undirected hyper graph.
 * @param vertices
 *   The vertices in the hyper graph.
 * @param edges
 *   The set of hyper edges in the hyper graph.
 * @tparam T
 *   The type of the vertices.
 */
case class UnDiHyperGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[UnDiHyperEdge[Node[T]]]
) extends HyperGraph[T, UnDiHyperEdge[Node[T]]] {
  override def +(vertex: Node[T]): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices ++ _vertices, edges)

  override def +(edge: UnDiHyperEdge[Node[T]]): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices ++ edge.left ++ edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[UnDiHyperEdge[Node[T]]]): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices ++ _edges.flatMap(_.left) ++ _edges.flatMap(_.right), edges ++ _edges)

  override def -(vertex: Node[T]): UnDiHyperGraph[T] =
    UnDiHyperGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: UnDiHyperEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): UnDiHyperGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: UnDiHyperEdge[Node[T]]): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[UnDiHyperEdge[Node[T]]]): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices, edges -- _edges)

  override def merge[G <: HyperGraph[T, UnDiHyperEdge[Node[T]]]](that: G): UnDiHyperGraph[T] =
    that match {
      case UnDiHyperGraph(thatVertices: Set[Node[T]], thatEdges: Set[UnDiHyperEdge[Node[T]]]) =>
        val v: Set[Node[T]] = this.vertices union thatVertices
        val e = this.edges union thatEdges
        UnDiHyperGraph(v, e)
    }
}

case object UnDiHyperGraph {
  /** Creates an undirected hyper graph without edges from a set of vertices.
   * @param vertices
   * A set of vertices.
   * @tparam T
   * The type of the undirected graph vertices.
   * @return
   * An undirected hyper graph of type T with only the vertices.
   */
  def apply[T](vertices: Set[Node[T]]): UnDiHyperGraph[T] = UnDiHyperGraph.empty + vertices

  /** Creates an undirected hyper graph from a set of hyper edges.
   *
   * Will automatically add the vertices from the edges, without explicitly passing them to the
   * constructor.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the undirected hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * An undirected hyper graph of type T.
   */
  def apply[T, X: ClassTag](edges: Set[UnDiHyperEdge[Node[T]]]): UnDiHyperGraph[T] =
    UnDiHyperGraph.empty + edges

  /** Creates an empty undirected hyper graph of type T.
   * @tparam T
   * The type of the undirected hyper graph vertices.
   * @return
   * An empty undirected hyper graph of type T.
   */
  def empty[T]: UnDiHyperGraph[T] = UnDiHyperGraph(Set[Node[T]](), Set[UnDiHyperEdge[Node[T]]]())

  /** Creates an undirected hyper graph from a set of base values and a set of hyper edges.
   *
   * Will map base values to the [[Node]] wrapper and then construct the undirected hyper graph.
   *
   * @param vertices
   * The set of base values representing the vertices.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the undirected hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * An undirected hyper graph.
   */
  def apply[T, X: ClassTag](
      vertices: Set[T],
      edges: Set[UnDiHyperEdge[Node[T]]]
  ): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices.map(N), edges)
}
