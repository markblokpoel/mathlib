package mathlib.graph
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

case class DiGraph[T](override val vertices: Set[Node[T]], override val edges: Set[DiEdge[Node[T]]])
    extends Graph[T, DiEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): DiGraph[T] =
    DiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): DiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: DiEdge[Node[T]]): DiGraph[T] =
    DiGraph(vertices + edge.left + edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[DiEdge[Node[T]]]): DiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): DiGraph[T] =
    DiGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: DiEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): DiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: DiEdge[Node[T]]): DiGraph[T] =
    DiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[DiEdge[Node[T]]]): DiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, DiEdge[Node[T]]]](that: G): DiGraph[T] = that match {
    case DiGraph(thatVertices: Set[Node[T]], thatEdges: Set[DiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e               = this.edges union thatEdges
      DiGraph(v, e)
  }
}

case object DiGraph {
  def apply[T](vertices: Set[Node[T]]): DiGraph[T]                   = DiGraph.empty + vertices
  def apply[T, X: ClassTag](edges: Set[DiEdge[Node[T]]]): DiGraph[T] = DiGraph.empty + edges
  def empty[T]: DiGraph[T]                                           = DiGraph(Set.empty, Set.empty)
  def random[T](objects: Set[T], p: Double): DiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => DiEdge(e._1, e._2))
    DiGraph(vertices, edges)
  }

  def random(n: Int, p: Double): DiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  def uniform[T](objects: Set[T], numberEdges: Int): DiGraph[T] = {
    val vertices = objects.map(Node(_))

    @tailrec
    def randomEdges(
        possibleEdges: Set[DiEdge[Node[T]]],
        numberEdges: Int,
        accNumberEdges: Int,
        edges: Set[DiEdge[Node[T]]]
    ): Set[DiEdge[Node[T]]] = {
      val randomEdge = possibleEdges.random // None is set is empty

      if(randomEdge.isEmpty || accNumberEdges == numberEdges) edges
      else
        randomEdges(
          possibleEdges - randomEdge.get,
          numberEdges,
          accNumberEdges + 1,
          edges + randomEdge.get
        )
    }

    val edges = randomEdges(
      possibleEdges = (vertices x vertices).map(e => DiEdge(e._1, e._2)),
      numberEdges,
      accNumberEdges = 0,
      edges = Set.empty
    )

    DiGraph(vertices, edges)
  }

  def uniform(n: Int, numberEdges: Int): DiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }
}
