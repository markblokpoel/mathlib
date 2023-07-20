package mathlib.graph.hyper

import mathlib.graph.GraphImplicits.N
import mathlib.graph.{DiEdge, DiGraph, Node}

import scala.reflect.ClassTag

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
  def apply[T](vertices: Set[Node[T]]): DiHyperGraph[T] = DiHyperGraph.empty + vertices

  def apply[T, X: ClassTag](edges: Set[DiHyperEdge[Node[T]]]): DiHyperGraph[T] = DiHyperGraph.empty + edges

  def empty[T]: DiHyperGraph[T] = DiHyperGraph(Set[Node[T]](), Set[DiHyperEdge[Node[T]]]())

  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[DiHyperEdge[Node[T]]]): DiHyperGraph[T] =
    DiHyperGraph(vertices.map(N), edges)
}