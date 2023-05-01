package mathlib.graph.hyper

import mathlib.graph.GraphImplicits.N
import mathlib.graph.Node

import scala.reflect.ClassTag

case class UnDiHyperGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[UnDiHyperEdge[Node[T]]]
) extends HyperGraph[T, UnDiHyperEdge[Node[T]]](vertices, edges) {
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
  def apply[T](vertices: Set[Node[T]]): UnDiHyperGraph[T] = UnDiHyperGraph.empty + vertices

  def apply[T, X: ClassTag](edges: Set[UnDiHyperEdge[Node[T]]]): UnDiHyperGraph[T] =
    UnDiHyperGraph.empty + edges

  def empty[T]: UnDiHyperGraph[T] = UnDiHyperGraph(Set[Node[T]](), Set[UnDiHyperEdge[Node[T]]]())

  def apply[T, X: ClassTag](
      vertices: Set[T],
      edges: Set[UnDiHyperEdge[Node[T]]]
  ): UnDiHyperGraph[T] =
    UnDiHyperGraph(vertices.map(N), edges)
}
