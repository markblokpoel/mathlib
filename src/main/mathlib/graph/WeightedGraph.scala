package mathlib.graph

import mathlib.graph.properties.{Edge, WeightedEdge}

import scala.annotation.tailrec

case class NodeWeightPair[T](node: Node[T], weight: Double) {
  def +(weight: Double): NodeWeightPair[T] = NodeWeightPair(node, this.weight + weight)
}
abstract class WeightedGraph[T, E <: Edge[Node[T]] with WeightedEdge](
    override val vertices: Set[Node[T]],
    override val edges: Set[E]
) extends Graph[T, E](vertices, edges) {

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

//  private def containsCycleRec(
//      currentVertex: Node[T],
//      visitedVertices: Set[Node[T]],
//      traversingGraph: WeightedGraph[T, E]
//  ): Boolean = {
//    traversingGraph.adjacencyList(currentVertex)
//      .map(nextVertex =>
//        containsCycleRec(nextVertex.node, visitedVertices + currentVertex, traversingGraph - currentVertex)
//
//
//  }
//
  override def containsCycle: Boolean = ???

}
