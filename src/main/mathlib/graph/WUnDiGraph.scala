package mathlib.graph
import scala.reflect.ClassTag

case class WUnDiGraph[T](override val vertices: Set[Node[T]], override val edges: Set[WUnDiEdge[Node[T]]])
  extends Graph[T,WUnDiEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): WUnDiGraph[T] =
    WUnDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): WUnDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: WUnDiEdge[Node[T]]): WUnDiGraph[T] =
    WUnDiGraph(vertices + edge.v1 + edge.v2, edges + edge)

  override def +[X: ClassTag](_edges: Set[WUnDiEdge[Node[T]]]): WUnDiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): WUnDiGraph[T] =
    WUnDiGraph(vertices - vertex, edges.filter(_ match {
      case edge: WUnDiEdge[Node[T]] => !edge.contains(vertex)
    }))

  override def -(_vertices: Set[Node[T]]): WUnDiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: WUnDiEdge[Node[T]]): WUnDiGraph[T] =
    WUnDiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[WUnDiEdge[Node[T]]]): WUnDiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, WUnDiEdge[Node[T]]]](that: G): WUnDiGraph[T] = that match {
    case WUnDiGraph(thatVertices: Set[Node[T]], thatEdges: Set[WUnDiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e = this.edges union thatEdges
      WUnDiGraph(v, e)
  }
}


case object WUnDiGraph {
  def apply[T](vertices: Set[Node[T]]): WUnDiGraph[T] = WUnDiGraph.empty + vertices
  def apply[T, X: ClassTag](edges: Set[WUnDiEdge[Node[T]]]): WUnDiGraph[T] = WUnDiGraph.empty + edges
  def empty[T]: WUnDiGraph[T] = WUnDiGraph(Set.empty, Set.empty)
}