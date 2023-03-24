
package mathlib.graph

import mathlib.graph.properties.Edge

import scala.reflect.ClassTag

case class UnDiGraph[T](override val vertices: Set[Node[T]], override val edges: Set[UnDiEdge[Node[T]]])
  extends Graph[T,UnDiEdge[Node[T]]](vertices, edges) {

  override def +(vertex: Node[T]): UnDiGraph[T] =
    UnDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): UnDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: UnDiEdge[Node[T]]): UnDiGraph[T] =
    UnDiGraph(vertices + edge.v1 + edge.v2, edges + edge)

  override def +[X: ClassTag](_edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): UnDiGraph[T] =
    UnDiGraph(vertices - vertex, edges.filter(_ match {
      case edge: Edge[Node[T]] => !edge.contains(vertex)
    }))

  override def -(_vertices: Set[Node[T]]): UnDiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

   override def -(edge: UnDiEdge[Node[T]]): UnDiGraph[T] =
    UnDiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, UnDiEdge[Node[T]]]](that: G): UnDiGraph[T] = that match {
    case UnDiGraph(thatVertices: Set[Node[T]], thatEdges: Set[UnDiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e = this.edges union thatEdges
      UnDiGraph(v, e)
  }
}

case object UnDiGraph {
  def apply[T](vertices: Set[Node[T]]): UnDiGraph[T] = UnDiGraph.empty + vertices
  def apply[T, X: ClassTag](edges: Set[Edge[Node[T]]]): UnDiGraph[T] = UnDiGraph.empty + edges
  def empty[T]: UnDiGraph[T] = UnDiGraph(Set.empty, Set.empty)
}