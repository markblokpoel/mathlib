package mathlib.graph.hyper

import mathlib.graph.GraphImplicits.N
import mathlib.graph.{DiEdge, DiGraph, Node}

import scala.reflect.ClassTag

/** Represents a directed hyper graph.
  * @param vertices
  *   The vertices in the hyper graph.
  * @param edges
  *   The set of hyper edges in the hyper graph.
  * @tparam T
  *   The type of the vertices.
  */
case class DiHyperGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[DiHyperEdge[Node[T]]]
) extends HyperGraph[T, DiHyperEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): DiHyperGraph[T] =
    DiHyperGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): DiHyperGraph[T] =
    DiHyperGraph(vertices ++ _vertices, edges)

  override def +(edge: DiHyperEdge[Node[T]]): DiHyperGraph[T] =
    DiHyperGraph(vertices ++ edge.left ++ edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[DiHyperEdge[Node[T]]]): DiHyperGraph[T] =
    DiHyperGraph(vertices ++ _edges.flatMap(_.left) ++ _edges.flatMap(_.right), edges ++ _edges)

  override def -(vertex: Node[T]): DiHyperGraph[T] =
    DiHyperGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: DiHyperEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): DiHyperGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: DiHyperEdge[Node[T]]): DiHyperGraph[T] =
    DiHyperGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[DiHyperEdge[Node[T]]]): DiHyperGraph[T] =
    DiHyperGraph(vertices, edges -- _edges)

  override def merge[G <: HyperGraph[T, DiHyperEdge[Node[T]]]](that: G): DiHyperGraph[T] =
    that match {
      case DiHyperGraph(thatVertices: Set[Node[T]], thatEdges: Set[DiHyperEdge[Node[T]]]) =>
        val v: Set[Node[T]] = this.vertices union thatVertices
        val e               = this.edges union thatEdges
        DiHyperGraph(v, e)
    }
}

case object DiHyperGraph {
  /** Creates a directed hyper graph without edges from a set of vertices.
   *
   * @param vertices
   * A set of vertices.
   * @tparam T
   * The type of the directed graph vertices.
   * @return
   * A directed hyper graph of type T with only the vertices.
   */
  def apply[T](vertices: Set[Node[T]]): DiHyperGraph[T] = DiHyperGraph.empty + vertices

  /** Creates a directed hyper graph from a set of hyper edges.
   *
   * Will automatically add the vertices from the edges, without explicitly passing them to the
   * constructor.
   *
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the directed hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * A directed hyper graph of type T.
   */
  def apply[T, X: ClassTag](edges: Set[DiHyperEdge[Node[T]]]): DiHyperGraph[T] =
    DiHyperGraph.empty + edges

  /** Creates an empty directed hyper graph of type T.
   *
   * @tparam T
   * The type of the directed graph vertices.
   * @return
   * An empty directed graph of type T.
   */
  def empty[T]: DiHyperGraph[T] = DiHyperGraph(Set[Node[T]](), Set[DiHyperEdge[Node[T]]]())

  /** Creates a directed graph from a set of base values and a set of hyper edges.
   *
   * Will map base values to the [[Node]] wrapper and then construct the directed hyper graph.
   *
   * @param vertices
   * The set of base values representing the vertices.
   * @param edges
   * The set of hyper edges.
   * @tparam T
   * The type of the directed hyper graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * A directed hyper graph.
   */
  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[DiHyperEdge[Node[T]]]): DiHyperGraph[T] =
    DiHyperGraph(vertices.map(N), edges)
}
