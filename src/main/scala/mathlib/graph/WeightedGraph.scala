package mathlib.graph

import mathlib.graph.properties.{Edge, WeightedEdge}
import mathlib.set.SetTheory.ImplAny

import scala.annotation.tailrec
import scala.reflect.ClassTag

/** A data structure to represent pairs of nodes and weights (of the path leading to this node).
  * @param node
  *   The node of type T.
  * @param weight
  *   The weight.
  * @tparam T
  *   Type of the node.
  */
case class NodeWeightPair[T](node: Node[T], weight: Double) {

  /** Add a weight to the pair.
    * @param weight
    *   The weight to be added.
    * @return
    *   Updated [[NodeWeightPair]].
    */
  def +(weight: Double): NodeWeightPair[T] = NodeWeightPair(node, this.weight + weight)
}

/** Trait representing weighted graphs.
  * @tparam T
  *   The type of nodes in the graph.
  * @tparam E
  *   The type of edges of the graph, must be a subtype of [[properties.WeightedEdge]].
  */
trait WeightedGraph[T, E <: Edge[Node[T]] with WeightedEdge] extends Graph[T, E] {

  override def degreeOf(vertex: Node[T]): Int =
    adjacencyList(vertex).size

  /** Computes the adjacency list for weighted graphs (either directed or undirected).
    *
    * @return
    *   A mapping from nodes to a set of neighboring nodes with the associated weights.
    */
  private def calcAdjacencyList(): Map[Node[T], Set[NodeWeightPair[T]]] =
    vertices
      .map(v => {
        val adjacents = edges
          .filter(e => (e contains v) && e.weight != 0)
          .map(e => {
            val adjacentOption = e.getNeighborOf(v)
            if (adjacentOption.isDefined) Some(NodeWeightPair(adjacentOption.get, e.weight))
            else None
          })
        v -> adjacents.filter(_.isDefined).map(_.get)
      })
      .toMap

  /** The adjacency list for this graph, represented as a mapping from nodes to a set of neighboring
    * nodes with associated weights. This is lazily (only when called) evaluated.
    */
  lazy val adjacencyList: Map[Node[T], Set[NodeWeightPair[T]]] = calcAdjacencyList()

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
      adjL: Map[Node[T], Set[NodeWeightPair[T]]]
  ): Map[Node[T], Set[NodeWeightPair[T]]] = {
    if (n == 0) adjL
    else {
      nthAdjacencyListRec(
        n - 1,
        adjL.map(kv => {
          val v                           = kv._1
          val adj: Set[NodeWeightPair[T]] = kv._2
          v -> adj.flatMap(a => adjacencyList(a.node).map(_ + a.weight))
        })
      )
    }
  }

  /** Recursively computes the n^th^ adjacency list.
    *
    * @param n
    *   The required level.
    * @return
    *   The n^th^ adjacency list.
    */
  def nthAdjacencyList(n: Int): Map[Node[T], Set[NodeWeightPair[T]]] = {
    nthAdjacencyListRec(n, adjacencyList)
  }

  /** Recursively checks if the graph contains a cycle.
    *
    * @param currentVertex
    *   The current vertex to be evaluated.
    * @param visitedVertices
    *   A set of vertices already visited.
    * @param traversingGraph
    *   The (partial) graph with removed edges to track traversal.
    * @return
    *  `true ` if the graph contains a cycle,  `false ` otherwise.
    */
  private def containsCycleRec(
      currentVertex: Node[T],
      visitedVertices: Set[Node[T]],
      traversingGraph: WeightedGraph[T, E]
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
                .asInstanceOf[WeightedGraph[T, E]] // can cast because must be a weighted graph
            )
        })
    }

  }

  /** Recursively checks if the graph contains a cycle.
    *
    * @return
    *  `true ` if the graph contains a cycle,  `false ` otherwise.
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
