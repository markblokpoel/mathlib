package mathlib.graph
import mathlib.graph.GraphImplicits.N
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

/** Represents an unweighted directed graph.
  * @param vertices
  *   The set of vertices of the graph.
  * @param edges
  *   The set of edges of the graph.
  * @tparam T
  *   The type of nodes in the graph.
  */
case class DiGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[DiEdge[Node[T]]]
) extends UnweightedGraph[T, DiEdge[Node[T]]](vertices, edges) {
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

/** Factory for directed graphs. */
case object DiGraph {

  /** Creates a directed graph without edges from a set of vertices.
    * @param vertices
    *   A set of vertices.
    * @tparam T
    *   The type of the directed graph vertices.
    * @return
    *   A directed graph of type T with only the vertices.
    */
  def apply[T](vertices: Set[Node[T]]): DiGraph[T] = DiGraph.empty + vertices

  /** Creates a directed graph from a set of edges.
    *
    * Will automatically add the vertices from the edges, without explicitly passing them to the
    * constructor.
    *
    * @param edges
    *   The set of edges.
    * @tparam T
    *   The type of the directed graph vertices.
    * @tparam X
    *   A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
    * @return
    *   A directed graph of type T.
    */
  def apply[T, X: ClassTag](edges: Set[DiEdge[Node[T]]]): DiGraph[T] = DiGraph.empty + edges

  /** Creates an empty directed graph of type T.
    * @tparam T
    *   The type of the directed graph vertices.
    * @return
    *   An empty directed graph of type T.
    */
  def empty[T]: DiGraph[T] = DiGraph(Set[Node[T]](), Set[DiEdge[Node[T]]]())

  /** Creates a directed graph from a set of base values and a set of edges.
    *
    * Will map base values to the [[Node]] wrapper and then construct the directed graph.
    * @param vertices
    *   The set of base values representing the vertices.
    * @param edges
    *   The set of edges.
    * @tparam T
    *   The type of the directed graph vertices.
    * @tparam X
    *   A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
    * @return
    *   A directed graph.
    */
  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[DiEdge[Node[T]]]): DiGraph[T] =
    DiGraph(vertices.map(N), edges)

  /** Generate a random directed graph.
    *
    * Graphs are generated according to the Erdős–Rényi–Gilbert model, which states that between
    * each pair of objects there is a p probability of an edge existing.
    * @param objects
    *   The set of objects representing the vertices.
    * @param p
    *   The probability of an edge existing.
    * @tparam T
    *   The type of the graph and objects.
    * @return
    *   A random graph.
    */
  def random[T](objects: Set[T], p: Double): DiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => DiEdge(e._1, e._2))
    DiGraph(vertices, edges)
  }

  /** Helper factory to generate a random directed graph of type [[java.lang.String]] for n
    * vertices.
    *
    * Uses the Erdős–Rényi–Gilbert model.
    *
    * @param n
    *   The number of vertices.
    * @param p
    *   The probability of an edge existing.
    * @return
    *   A random graph.
    */
  def random(n: Int, p: Double): DiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  /** Generate a random directed graph.
    *
    * Graphs are generated according to the uniform model, which states that given a number of fixed
    * edges will uniformly distribute those edges between all pairs of objects. The probability of
    * an edge existing in the final graph is the same as in the Erdős–Rényi–Gilbert model.
    * @param objects
    *   The set of objects representing the vertices.
    * @param numberEdges
    *   The number of edges.
    * @tparam T
    *   The type of the graph and objects.
    * @return
    *   A random graph.
    */
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
      possibleEdges = (vertices x vertices).map(e => DiEdge(e._1, e._2)),
      numberEdges,
      accNumberEdges = 0,
      edges = Set.empty
    )

    DiGraph(vertices, edges)
  }

  /** Helper factory to generate a random directed graph of type [[java.lang.String]] for n
    * vertices.
    *
    * Uses the uniform model.
    *
    * @param n
    *   The number of vertices.
    * @param numberEdges
    *   The number of edges.
    * @return
    *   A random graph.
    */
  def uniform(n: Int, numberEdges: Int): DiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }
}
