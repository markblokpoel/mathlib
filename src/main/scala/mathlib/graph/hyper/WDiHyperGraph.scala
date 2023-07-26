package mathlib.graph.hyper
import mathlib.graph.GraphImplicits.N
import mathlib.graph.Node

import scala.reflect.ClassTag

/** Represents a weighted directed hyper graph.
 * @param vertices
 *   The vertices in the hyper graph.
 * @param edges
 *   The set of hyper edges in the hyper graph.
 * @tparam T
 *   The type of the vertices.
 */
case class WDiHyperGraph[T](override val vertices: Set[Node[T]], override val edges: Set[WDiHyperEdge[Node[T]]])
  extends HyperGraph[T, WDiHyperEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices ++ _vertices, edges)

  override def +(edge: WDiHyperEdge[Node[T]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices ++ edge.left ++ edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[WDiHyperEdge[Node[T]]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices ++ _edges.flatMap(_.left) ++ _edges.flatMap(_.right), edges ++ _edges)

  override def -(vertex: Node[T]): WDiHyperGraph[T] =
    WDiHyperGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: WDiHyperEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): WDiHyperGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: WDiHyperEdge[Node[T]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[WDiHyperEdge[Node[T]]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices, edges -- _edges)

  override def merge[G <: HyperGraph[T, WDiHyperEdge[Node[T]]]](that: G): WDiHyperGraph[T] =
    that match {
      case WDiHyperGraph(thatVertices: Set[Node[T]], thatEdges: Set[WDiHyperEdge[Node[T]]]) =>
        val v: Set[Node[T]] = this.vertices union thatVertices
        val e = this.edges union thatEdges
        WDiHyperGraph(v, e)
    }
}

case object WDiHyperGraph {
  /** Creates a weighted directed hyper graph without edges from a set of vertices.
   * @param vertices
   * A set of vertices.
   * @tparam T
   * The type of the weighted directed graph vertices.
   * @return
   * A weighted directed hyper graph of type T with only the vertices.
   */
  def apply[T](vertices: Set[Node[T]]): WDiHyperGraph[T] = WDiHyperGraph.empty + vertices

  /** Creates a weighted directed hyper graph from a set of hyper edges.
   *
   * Will automatically add the vertices from the edges, without explicitly passing them to the
   * constructor.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the weighted directed hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * A weighted directed hyper graph of type T.
   */
  def apply[T, X: ClassTag](edges: Set[WDiHyperEdge[Node[T]]]): WDiHyperGraph[T] = WDiHyperGraph.empty + edges

  /** Creates an empty weighted directed hyper graph of type T.
   * @tparam T
   * The type of the directed hyper graph vertices.
   * @return
   * An empty directed hyper graph of type T.
   */
  def empty[T]: WDiHyperGraph[T] = WDiHyperGraph(Set[Node[T]](), Set[WDiHyperEdge[Node[T]]]())

  /** Creates a weighted directed hyper graph from a set of base values and a set of hyper edges.
   *
   * Will map base values to the [[Node]] wrapper and then construct the directed hyper graph.
   * @param vertices
   * The set of base values representing the vertices.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the weighted directed hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * A weighted directed hyper graph.
   */
  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[WDiHyperEdge[Node[T]]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices.map(N), edges)
}