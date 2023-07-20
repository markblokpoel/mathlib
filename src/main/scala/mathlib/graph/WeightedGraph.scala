package mathlib.graph

import mathlib.graph.properties.{Edge, WeightedEdge}
import mathlib.set.SetTheory.ImplAny

import scala.annotation.tailrec
import scala.reflect.ClassTag

case class NodeWeightPair[T](node: Node[T], weight: Double) {
  def +(weight: Double): NodeWeightPair[T] = NodeWeightPair(node, this.weight + weight)
}
abstract class WeightedGraph[T, E <: Edge[Node[T]] with WeightedEdge](
    override val vertices: Set[Node[T]],
    override val edges: Set[E]
) extends Graph[T, E](vertices, edges) {

//  override def +(vertex: Node[T]): WeightedGraph[T, E]
//
//  override def +(_vertices: Set[Node[T]]): WeightedGraph[T, E]
//
//  override def +(edge: E): WeightedGraph[T, E]
//
//  override def +[X: ClassTag](_edges: Set[E]): WeightedGraph[T, E]
//
//  override def -(vertex: Node[T]): WeightedGraph[T, E]
//
//  override def -(_vertices: Set[Node[T]]): WeightedGraph[T, E]
//
//  override def -(edge: E): WeightedGraph[T, E]
//
//  override def -[X: ClassTag](_edges: Set[E]): WeightedGraph[T, E]
//
//  override def merge[G <: Graph[T, E]](that: G): WeightedGraph[T, E]

  def calcAdjacencyList(): Map[Node[T], Set[NodeWeightPair[T]]] =
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

  lazy val adjacencyList: Map[Node[T], Set[NodeWeightPair[T]]] = calcAdjacencyList()

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

  def nthAdjacencyList(n: Int): Map[Node[T], Set[NodeWeightPair[T]]] = {
    nthAdjacencyListRec(n, adjacencyList)
  }

  private def containsCycleRec(
      currentVertex: Node[T],
      visitedVertices: Set[Node[T]],
      traversingGraph: WeightedGraph[T, E]
  ): Boolean = {
    if (traversingGraph.noEdges && (currentVertex in visitedVertices)) true
    else if (traversingGraph.noEdges) false
    else {
      traversingGraph
      .edges
      .filter(_ contains currentVertex)
      .exists(edge => {
        // check if it leads to a cycle
        val neighbor = edge.getNeighborOf(currentVertex)
        if (neighbor.isEmpty) false
        else
          containsCycleRec(
            neighbor.get,
            visitedVertices + currentVertex,
            (traversingGraph - edge).asInstanceOf[WeightedGraph[T, E]] // can cast because must be a weighted graph
          )
      })
    }

  }

  override def containsCycle: Boolean =
    vertices  // forall vertices check if it leads to a cycle (in case of forests)
      .exists(vertex =>
        containsCycleRec(
          vertex,
          Set(),
          this
        )
      )
}
