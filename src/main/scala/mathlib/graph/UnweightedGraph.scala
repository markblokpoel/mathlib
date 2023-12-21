package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplAny

import scala.annotation.tailrec

/** Abstract class representing unweighted graphs.
  * @param vertices
  *   The set of vertices of the graph.
  * @param edges
  *   The set of edges of the graph.
  * @tparam T
  *   The type of nodes in the graph.
  * @tparam E
  *   The type of edges of the graph, must be a subtype of [[properties.Edge]].
  */
abstract class UnweightedGraph[T, E <: Edge[Node[T]]](
    override val vertices: Set[Node[T]],
    override val edges: Set[E]
) extends Graph[T, E](vertices, edges) {

  override def degreeOf(vertex: Node[T]): Int =
    adjacencyList(vertex).size

  /** Computes the adjacency list for unweighted graphs (either directed or undirected).
    * @return
    *   A mapping from nodes to a set of neighboring nodes.
    */
  private def calcAdjacencyList(): Map[Node[T], Set[Node[T]]] =
    vertices
      .map(v => {
        val adjacents = edges
          .filter(_ contains v)
          .map(e => {
            val adjacentOption = e.getNeighborOf(v)
            if (adjacentOption.isDefined) Some(adjacentOption.get)
            else None
          })
        v -> adjacents.filter(_.isDefined).map(_.get)
      })
      .toMap

  /** The adjacency list for this graph, represented as a mapping from nodes to a set of neighboring
    * nodes. This is lazily (only when called) evaluated.
    */
  lazy val adjacencyList: Map[Node[T], Set[Node[T]]] = calcAdjacencyList()

  /** Recursively computes the n^th^ adjacency list.
    *
    * @param n
    *   The required level.
    * @param adjL
    *   Current adjacency list.
    * @return
    *   The n^th^ adjacency list.
    */
  @tailrec
  private def nthAdjacencyListRec(
      n: Int,
      adjL: Map[Node[T], Set[Node[T]]]
  ): Map[Node[T], Set[Node[T]]] = {
    if (n == 0) adjL
    else {
      nthAdjacencyListRec(
        n - 1,
        adjL.map(kv => {
          val v                 = kv._1
          val adj: Set[Node[T]] = kv._2
          v -> adj.flatMap(a => adjacencyList(a))
        })
      )
    }
  }

  /** Recursively computes the n^th^ adjacency list.
    * @param n
    *   The required level.
    * @return
    *   The n^th^ adjacency list.
    */
  def nthAdjacencyList(n: Int): Map[Node[T], Set[Node[T]]] = {
    nthAdjacencyListRec(n, adjacencyList)
  }

  /** Recursively checks if the graph contains a cycle.
    * @param currentVertex
    *   The current vertex to be evaluated.
    * @param visitedVertices
    *   A set of vertices already visited.
    * @param traversingGraph
    *   The (partial) graph with removed edges to track traversal.
    * @return
    *   ```true``` if the graph contains a cycle, ```false``` otherwise.
    */
  private def containsCycleRec(
      currentVertex: Node[T],
      visitedVertices: Set[Node[T]],
      traversingGraph: UnweightedGraph[T, E]
  ): Boolean = {
    if (traversingGraph.noEdges && (currentVertex in visitedVertices)) true
    else if (traversingGraph.noEdges) false
    else {
      traversingGraph.edges
        .filter(_ contains currentVertex)
        .exists(edge => {
          // check if it leads to a cycle
          val neighbor = edge.getNeighborOf(currentVertex)
          if (neighbor.isEmpty) false
          else
            containsCycleRec(
              neighbor.get,
              visitedVertices + currentVertex,
              (traversingGraph - edge)
                .asInstanceOf[UnweightedGraph[T, E]] // can cast because must be a weighted graph
            )
        })
    }

  }

  /** Recursively checks if the graph contains a cycle.
    * @return
    *   ```true``` if the graph contains a cycle, ```false``` otherwise.
    */
  override def containsCycle: Boolean =
    vertices // forall vertices check if it leads to a cycle (in case of forests)
      .exists(vertex =>
        containsCycleRec(
          vertex,
          Set(),
          this
        )
      )
}
