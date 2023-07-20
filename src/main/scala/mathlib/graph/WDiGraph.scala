package mathlib.graph
import mathlib.graph.GraphImplicits.N
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

case class WDiGraph[T](override val vertices: Set[Node[T]],override val edges: Set[WDiEdge[Node[T]]])
  extends WeightedGraph[T, WDiEdge[Node[T]]](vertices, edges) {
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
  def empty[T]: WDiGraph[T] = WDiGraph(Set[Node[T]](), Set[WDiEdge[Node[T]]]())

  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] =
    WDiGraph(vertices.map(N), edges)

  def random[T](objects: Set[T], p: Double): WDiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => WDiEdge(e._1, e._2, Random.nextDouble()))
    WDiGraph(vertices, edges)
  }

  def random(n: Int, p: Double): WDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  def uniform[T](objects: Set[T], numberEdges: Int): WDiGraph[T] = {
    val vertices = objects.map(Node(_))

    @tailrec
    def randomEdges(
                     possibleEdges: Set[WDiEdge[Node[T]]],
                     numberEdges: Int,
                     accNumberEdges: Int,
                     edges: Set[WDiEdge[Node[T]]]
                   ): Set[WDiEdge[Node[T]]] = {
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
      possibleEdges = (vertices x vertices).map(e => WDiEdge(e._1, e._2, Random.nextDouble())),
      numberEdges,
      accNumberEdges = 0,
      edges = Set.empty
    )

    WDiGraph(vertices, edges)
  }

  def uniform(n: Int, numberEdges: Int): WDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }
}