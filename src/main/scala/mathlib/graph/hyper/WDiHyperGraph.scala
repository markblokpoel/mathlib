package mathlib.graph.hyper
import mathlib.graph.GraphImplicits.N
import mathlib.graph.Node

import scala.reflect.ClassTag

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
  def apply[T](vertices: Set[Node[T]]): WDiHyperGraph[T] = WDiHyperGraph.empty + vertices

  def apply[T, X: ClassTag](edges: Set[WDiHyperEdge[Node[T]]]): WDiHyperGraph[T] = WDiHyperGraph.empty + edges

  def empty[T]: WDiHyperGraph[T] = WDiHyperGraph(Set[Node[T]](), Set[WDiHyperEdge[Node[T]]]())

  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[WDiHyperEdge[Node[T]]]): WDiHyperGraph[T] =
    WDiHyperGraph(vertices.map(N), edges)
}