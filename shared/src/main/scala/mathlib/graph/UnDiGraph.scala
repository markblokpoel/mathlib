package mathlib.graph

import mathlib.graph.properties.Edge

import scala.reflect.ClassTag

case class UnDiGraph[T](vertices: Set[Node[T]], edges: Set[UnDiEdge[Node[T]]])
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

  def merge(that: UnDiGraph[T]): UnDiGraph[T] = {
    val v: Set[Node[T]] = this.vertices union that.vertices
    val e = this.edges union that.edges
    UnDiGraph(v, e)
  }
}
