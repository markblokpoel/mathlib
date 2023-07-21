package mathlib.graph
import mathlib.graph.GraphImplicits.N
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.util.Random

/** Represents a weighted directed graph.
  * @param vertices
  *   The set of vertices of the graph.
  * @param edges
  *   The set of weighted edges of the graph.
  * @tparam T
  *   The type of nodes in the graph.
  */
case class WDiGraph[T](
    override val vertices: Set[Node[T]],
    override val edges: Set[WDiEdge[Node[T]]]
) extends WeightedGraph[T, WDiEdge[Node[T]]](vertices, edges) {
  override def +(vertex: Node[T]): WDiGraph[T] =
    WDiGraph(vertices + vertex, edges)

  override def +(_vertices: Set[Node[T]]): WDiGraph[T] =
    _vertices.foldLeft(this)(_ + _)

  override def +(edge: WDiEdge[Node[T]]): WDiGraph[T] =
    WDiGraph(vertices + edge.left + edge.right, edges + edge)

  override def +[X: ClassTag](_edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] =
    _edges.foldLeft(this)(_ + _)

  override def -(vertex: Node[T]): WDiGraph[T] =
    WDiGraph(
      vertices - vertex,
      edges.filter(_ match {
        case edge: WDiEdge[Node[T]] => !edge.contains(vertex)
      })
    )

  override def -(_vertices: Set[Node[T]]): WDiGraph[T] =
    _vertices.foldLeft(this)(_ - _)

  override def -(edge: WDiEdge[Node[T]]): WDiGraph[T] =
    WDiGraph(vertices, edges - edge)

  override def -[X: ClassTag](_edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] =
    _edges.foldLeft(this)(_ - _)

  override def merge[G <: Graph[T, WDiEdge[Node[T]]]](that: G): WDiGraph[T] = that match {
    case WDiGraph(thatVertices: Set[Node[T]], thatEdges: Set[WDiEdge[Node[T]]]) =>
      val v: Set[Node[T]] = this.vertices union thatVertices
      val e               = this.edges union thatEdges
      WDiGraph(v, e)
  }
}

/** Factory for weighted directed graphs. */
case object WDiGraph {

  /** Creates a weighted directed graph without edges from a set of vertices.
    *
    * @param vertices
    *   A set of vertices.
    * @tparam T
    *   The type of the directed graph vertices.
    * @return
    *   A directed graph of type T with only the vertices.
    */
  def apply[T](vertices: Set[Node[T]]): WDiGraph[T] = WDiGraph.empty + vertices

  /** Creates a weighted directed graph from a set of edges.
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
    *   A directed graph of type T with only the vertices.
    */
  def apply[T, X: ClassTag](edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] = WDiGraph.empty + edges

  /** Creates an empty weighted directed graph of type T.
    *
    * @tparam T
    *   The type of the directed graph vertices.
    * @return
    *   An empty directed graph of type T.
    */
  def empty[T]: WDiGraph[T] = WDiGraph(Set[Node[T]](), Set[WDiEdge[Node[T]]]())

  /** Creates a weighted directed graph from a set of base values and a set of edges.
    *
    * Will map base values to the [[Node]] wrapper and then construct the directed graph.
    *
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
  def apply[T, X: ClassTag](vertices: Set[T], edges: Set[WDiEdge[Node[T]]]): WDiGraph[T] =
    WDiGraph(vertices.map(N), edges)

  /** Generate a random weighted directed graph.
    *
    * Graphs are generated according to the Erdős–Rényi–Gilbert model, which states that between
    * each pair of objects there is a p probability of an edge existing. Weights are random sampled
    * between 0 and 1.
    *
    * @param objects
    *   The set of objects representing the vertices.
    * @param p
    *   The probability of an edge existing.
    * @tparam T
    *   The type of the graph and objects.
    * @return
    *   A random graph.
    */
  def random[T](objects: Set[T], p: Double): WDiGraph[T] = {
    val vertices = objects.map(Node(_))
    val edges = (vertices x vertices)
      .filter(_ => Random.nextDouble() <= p) // Filter edges by probability p
      .map(e => WDiEdge(e._1, e._2, Random.nextDouble()))
    WDiGraph(vertices, edges)
  }

  /** Helper factory to generate a random weighted directed graph of type [[java.lang.String]] for n
    * vertices.
    *
    * Uses the Erdős–Rényi–Gilbert model. Weights are random sampled between 0 and 1.
    *
    * @param n
    *   The number of vertices.
    * @param p
    *   The probability of an edge existing.
    * @return
    *   A random graph.
    */
  def random(n: Int, p: Double): WDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    random(objects, p)
  }

  /** Generate a random weighted directed graph.
   *
   * Graphs are generated according to the uniform model, which states that given a number of fixed edges
   * will uniformly distribute those edges between all pairs of objects. The probability of an edge existing
   * in the final graph is the same as in the Erdős–Rényi–Gilbert model.
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

  /** Helper factory to generate a random weighted directed graph of type [[java.lang.String]] for n
   * vertices.
   *
   * Uses the uniform model.
   *
   * @param n
   * The number of vertices.
   * @param numberEdges
   * The number of edges.
   * @return
   * A random graph.
   */
  def uniform(n: Int, numberEdges: Int): WDiGraph[String] = {
    val objects = (0 to n).toSet.map("N" + _)
    uniform(objects, numberEdges)
  }
}
