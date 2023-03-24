package mathlib.graph
import scala.reflect.ClassTag

case class WDiGraph[T](override val vertices: Set[Node[T]],override val edges: Set[WDiEdge[Node[T]]])
  extends Graph[T, WDiEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): WDiGraph[T] =
    WDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): WDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: WDiEdge[Node[T]]): WDiGraph[T] =
    WDiGraph(vertices + edge.left + edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): WDiGraph[T] =
    WDiGraph(vertices - vertex, edges.filter(_ match {
      case edge: WDiEdge[Node[T]] => !edge.contains(vertex)
    }))

  override def -(_vertices: Set[Node[T]]): WDiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: WDiEdge[Node[T]]): WDiGraph[T] =
    WDiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, WDiEdge[Node[T]]]](that: G): WDiGraph[T] = that match {
    case WDiGraph(thatVertices: Set[Node[T]], thatEdges: Set[WDiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e = this.edges union thatEdges
      WDiGraph(v, e)
  }
}

case object WDiGraph {
  def apply[T](vertices: Set[Node[T]]): WDiGraph[T] = WDiGraph.empty + vertices
  def apply[T, X: ClassTag](edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] = WDiGraph.empty + edges
  def empty[T]: WDiGraph[T] = WDiGraph(Set.empty, Set.empty)
}