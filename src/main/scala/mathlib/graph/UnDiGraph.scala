package mathlib.graph

import mathlib.graph.GraphImplicits.N
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

/** Represents an unweighted undirected graph.
  * @param vertices
  *   The set of vertices of the graph.
  * @param edges
  *   The set of edges of the graph.
  * @tparam T
  *   The type of nodes in the graph.
  */
case class UnDiGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[UnDiEdge[Node[T]]]
) extends UnweightedGraph[T, UnDiEdge[Node[T]]](vertices, edges) {

  override def +(vertex: Node[T]): UnDiGraph[T] =
    UnDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): UnDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: UnDiEdge[Node[T]]): UnDiGraph[T] =
    UnDiGraph(vertices + edge.left + edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): UnDiGraph[T] =
    UnDiGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: UnDiEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): UnDiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: UnDiEdge[Node[T]]): UnDiGraph[T] =
    UnDiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, UnDiEdge[Node[T]]]](that: G): UnDiGraph[T] = that match {
    case UnDiGraph(thatVertices: Set[Node[T]], thatEdges: Set[UnDiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e               = this.edges union thatEdges
      UnDiGraph(v, e)
  }
}

case object UnDiGraph {
  /** Creates an undirected graph without edges from a set of vertices.
   *
   * @param vertices
   * A set of vertices.
   * @tparam T
   * The type of the undirected graph vertices.
   * @return
   * An undirected graph of type T with only the vertices.
   */
  def apply[T](vertices: Set[Node[T]]): UnDiGraph[T] = UnDiGraph.empty + vertices

  /** Creates an undirected graph from a set of edges.
   *
   * Will automatically add the vertices from the edges, without explicitly passing them to the
   * constructor.
   *
   * @param edges
   * The set of edges.
   * @tparam T
   * The type of the undirected graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * An undirected graph of type T with only the vertices.
   */
  def apply[T, X: ClassTag](edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] = UnDiGraph.empty + edges

  /** Creates an empty undirected graph of type T.
   *
   * @tparam T
   * The type of the undirected graph vertices.
   * @return
   * An empty undirected graph of type T.
   */
  def empty[T]: UnDiGraph[T] = UnDiGraph(Set[Node[T]](), Set[UnDiEdge[Node[T]]]())

  /** Creates an undirected graph from a set of base values and a set of edges.
   *
   * Will map base values to the [[Node]] wrapper and then construct the undirected graph.
   *
   * @param vertices
   * The set of base values representing the vertices.
   * @param edges
   * The set of edges.
   * @tparam T
   * The type of the undirected graph vertices.
   * @tparam X
   * A [[scala.reflect.ClassTag]] to prevent type erasure of the edges.
   * @return
   * An undirected graph.
   */
  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[UnDiEdge[Node[T]]]): UnDiGraph[T] =
    UnDiGraph(vertices.map(N), edges)

  /** Generate a random undirected graph.
   *
   * Graphs are generated according to the Erdős–Rényi–Gilbert model, which states that between
   * each pair of objects there is a p probability of an edge existing.
   *
   * @param objects
   * The set of objects representing the vertices.
   * @param p
   * The probability of an edge existing.
   * @tparam T
   * The type of the graph and objects.
   * @return
   * A random graph.
   */
  def random[T](objects: Set[T], p: Double): UnDiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => UnDiEdge(e._1, e._2))
    UnDiGraph(vertices, edges)
  }

  /** Helper factory to generate a random undirected graph of type [[java.lang.String]] for n
   * vertices.
   *
   * Uses the Erdős–Rényi–Gilbert model.
   *
   * @param n
   * The number of vertices.
   * @param p
   * The probability of an edge existing.
   * @return
   * A random graph.
   */
  def random(n: Int, p: Double): UnDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  /** Generate a random undirected graph.
   *
   * Graphs are generated according to the uniform model, which states that given a number of fixed
   * edges will uniformly distribute those edges between all pairs of objects. The probability of
   * an edge existing in the final graph is the same as in the Erdős–Rényi–Gilbert model.
   *
   * @param objects
   * The set of objects representing the vertices.
   * @param numberEdges
   * The number of edges.
   * @tparam T
   * The type of the graph and objects.
   * @return
   * A random graph.
   */
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

  /** Helper factory to generate a random undirected graph of type [[java.lang.String]] for n
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
  def uniform(n: Int, numberEdges: Int): UnDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }
}
