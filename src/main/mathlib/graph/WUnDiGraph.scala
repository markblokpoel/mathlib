package mathlib.graph
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

case class WUnDiGraph[T](override val vertices: Set[Node[T]], override val edges: Set[WUnDiEdge[Node[T]]])
  extends Graph[T,WUnDiEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): WUnDiGraph[T] =
    WUnDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): WUnDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: WUnDiEdge[Node[T]]): WUnDiGraph[T] =
    WUnDiGraph(vertices + edge.left + edge.right, edges + edge)

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

  def random[T](objects: Set[T], p: Double): WUnDiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => WUnDiEdge(e._1, e._2, Random.nextDouble()))
    WUnDiGraph(vertices, edges)
  }

  def random(n: Int, p: Double): WUnDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  def uniform[T](objects: Set[T], numberEdges: Int): WUnDiGraph[T] = {
    val vertices = objects.map(Node(_))

    @tailrec
    def randomEdges(
                     possibleEdges: Set[WUnDiEdge[Node[T]]],
                     numberEdges: Int,
                     accNumberEdges: Int,
                     edges: Set[WUnDiEdge[Node[T]]]
                   ): Set[WUnDiEdge[Node[T]]] = {
      val randomEdge = possibleEdges.random // None is set is empty

      if (randomEdge.isEmpty || accNumberEdges == numberEdges) edges
      else
        randomEdges(
          possibleEdges - randomEdge.get,
          numberEdges,
          accNumberEdges + 1,
          edges + randomEdge.get
        )
    }

    val edges = randomEdges(
      possibleEdges = (vertices x vertices).map(e => WUnDiEdge(e._1, e._2, Random.nextDouble())),
      numberEdges,
      accNumberEdges = 0,
      edges = Set.empty
    )

    WUnDiGraph(vertices, edges)
  }

  def uniform(n: Int, numberEdges: Int): WUnDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }

}