package mathlib.graph

import mathlib.graph.GraphImplicits.N
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

case class UnDiGraph[T](override val vertices: Set[Node[T]], override val edges: Set[UnDiEdge[Node[T]]])
  extends UnweightedGraph[T,UnDiEdge[Node[T]]](vertices, edges) {

  override def +(vertex: Node[T]): UnDiGraph[T] =
    UnDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): UnDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: UnDiEdge[Node[T]]): UnDiGraph[T] =
    UnDiGraph(vertices + edge.left + edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): UnDiGraph[T] =
    UnDiGraph(vertices - vertex, edges.filter(_ match {
      case edge: UnDiEdge[Node[T]] => !edge.contains(vertex)
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
  def apply[T, X: ClassTag](edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] = UnDiGraph.empty + edges
  def empty[T]: UnDiGraph[T] = UnDiGraph(Set[Node[T]](), Set[UnDiEdge[Node[T]]]())

  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    UnDiGraph(vertices.map(N), edges)

  def random[T](objects: Set[T], p: Double): UnDiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => UnDiEdge(e._1, e._2))
    UnDiGraph(vertices, edges)
  }

  def random(n: Int, p: Double): UnDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  def uniform[T](objects: Set[T], numberEdges: Int): UnDiGraph[T] = {
    val vertices = objects.map(Node(_))

    @tailrec
    def randomEdges(
                     possibleEdges: Set[UnDiEdge[Node[T]]],
                     numberEdges: Int,
                     accNumberEdges: Int,
                     edges: Set[UnDiEdge[Node[T]]]
                   ): Set[UnDiEdge[Node[T]]] = {
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
      possibleEdges = (vertices x vertices).map(e => UnDiEdge(e._1, e._2)),
      numberEdges,
      accNumberEdges = 0,
      edges = Set.empty
    )

    UnDiGraph(vertices, edges)
  }

  def uniform(n: Int, numberEdges: Int): UnDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }
}