package mathlib.graph.hyper

import mathlib.graph.GraphImplicits.N
import mathlib.graph.Node

import scala.reflect.ClassTag

/** Represents a weighted undirected hyper graph.
 * @param vertices
 *   The vertices in the hyper graph.
 * @param edges
 *   The set of hyper edges in the hyper graph.
 * @tparam T
 *   The type of the vertices.
 */
case class WUnDiHyperGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[WUnDiHyperEdge[Node[T]]]
) extends HyperGraph[T, WUnDiHyperEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices ++ _vertices, edges)

  override def +(edge: WUnDiHyperEdge[Node[T]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices ++ edge.left ++ edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[WUnDiHyperEdge[Node[T]]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices ++ _edges.flatMap(_.left) ++ _edges.flatMap(_.right), edges ++ _edges)

  override def -(vertex: Node[T]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: WUnDiHyperEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): WUnDiHyperGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: WUnDiHyperEdge[Node[T]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[WUnDiHyperEdge[Node[T]]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices, edges -- _edges)

  override def merge[G <: HyperGraph[T, WUnDiHyperEdge[Node[T]]]](that: G): WUnDiHyperGraph[T] =
    that match {
      case WUnDiHyperGraph(thatVertices: Set[Node[T]], thatEdges: Set[WUnDiHyperEdge[Node[T]]]) =>
        val v: Set[Node[T]] = this.vertices union thatVertices
        val e               = this.edges union thatEdges
        WUnDiHyperGraph(v, e)
    }
}

case object WUnDiHyperGraph {
  /** Creates a weighted undirected hyper graph without edges from a set of vertices.
   * @param vertices
   * A set of vertices.
   * @tparam T
   * The type of the weighted undirected graph vertices.
   * @return
   * A weighted undirected hyper graph of type T with only the vertices.
   */
  def apply[T](vertices: Set[Node[T]]): WUnDiHyperGraph[T] = WUnDiHyperGraph.empty + vertices

  /** Creates a weighted undirected hyper graph from a set of hyper edges.
   *
   * Will automatically add the vertices from the edges, without explicitly passing them to the
   * constructor.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the weighted undirected hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * A weighted undirected hyper graph of type T.
   */
  def apply[T, X: ClassTag](edges: Set[WUnDiHyperEdge[Node[T]]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph.empty + edges

  /** Creates an empty weighted undirected hyper graph of type T.
   * @tparam T
   * The type of the weighted undirected hyper graph vertices.
   * @return
   * An empty weighted undirected hyper graph of type T.
   */
  def empty[T]: WUnDiHyperGraph[T] = WUnDiHyperGraph(Set[Node[T]](), Set[WUnDiHyperEdge[Node[T]]]())

  /** Creates a weighted undirected hyper graph from a set of base values and a set of hyper edges.
   *
   * Will map base values to the [[Node]] wrapper and then construct the undirected hyper graph.
   * @param vertices
   * The set of base values representing the vertices.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the weighted undirected hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * A weighted undirected hyper graph.
   */
  def apply[T, X: ClassTag](
      vertices: Set[T],
      edges: Set[WUnDiHyperEdge[Node[T]]]
  ): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices.map(N), edges)
}
