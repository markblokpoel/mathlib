package mathlib.graph.hyper

import mathlib.graph.GraphImplicits.N
import mathlib.graph.Node

import scala.reflect.ClassTag

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
  def apply[T](vertices: Set[Node[T]]): WUnDiHyperGraph[T] = WUnDiHyperGraph.empty + vertices

  def apply[T, X: ClassTag](edges: Set[WUnDiHyperEdge[Node[T]]]): WUnDiHyperGraph[T] =
    WUnDiHyperGraph.empty + edges

  def empty[T]: WUnDiHyperGraph[T] = WUnDiHyperGraph(Set[Node[T]](), Set[WUnDiHyperEdge[Node[T]]]())

  def apply[T, X: ClassTag](
      vertices: Set[T],
      edges: Set[WUnDiHyperEdge[Node[T]]]
  ): WUnDiHyperGraph[T] =
    WUnDiHyperGraph(vertices.map(N), edges)
}
