package mathlib.graph
import scala.reflect.ClassTag

case class DiGraph[T](override val vertices: Set[Node[T]], override val edges: Set[DiEdge[Node[T]]])
  extends Graph[T, DiEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): DiGraph[T] =
    DiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): DiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: DiEdge[Node[T]]): DiGraph[T] =
    DiGraph(vertices + edge.v1 + edge.v2, edges + edge)

  override def +[X: ClassTag](_edges: Set[DiEdge[Node[T]]]): DiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): DiGraph[T] =
    DiGraph(vertices - vertex, edges.filter(_ match {
      case edge: DiEdge[Node[T]] => !edge.contains(vertex)
    }))

  override def -(_vertices: Set[Node[T]]): DiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: DiEdge[Node[T]]): DiGraph[T] =
    DiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[DiEdge[Node[T]]]): DiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, DiEdge[Node[T]]]](that: G): DiGraph[T] = that match {
    case DiGraph(thatVertices: Set[Node[T]], thatEdges: Set[DiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e = this.edges union thatEdges
      DiGraph(v, e)
  }
}

case object DiGraph {
  def apply[T](vertices: Set[Node[T]]): DiGraph[T] = DiGraph.empty + vertices
  def apply[T, X: ClassTag](edges: Set[DiEdge[Node[T]]]): DiGraph[T] = DiGraph.empty + edges
  def empty[T]: DiGraph[T] = DiGraph(Set.empty, Set.empty)
}
