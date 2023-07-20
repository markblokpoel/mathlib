package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplAny

import scala.annotation.tailrec

abstract class UnweightedGraph[T, E <: Edge[Node[T]]](
    override val vertices: Set[Node[T]],
    override val edges: Set[E]
) extends Graph[T, E](vertices, edges) {

  def calcAdjacencyList(): Map[Node[T], Set[Node[T]]] =
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

  lazy val adjacencyList: Map[Node[T], Set[Node[T]]] = calcAdjacencyList()

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

  def nthAdjacencyList(n: Int): Map[Node[T], Set[Node[T]]] = {
    nthAdjacencyListRec(n, adjacencyList)
  }

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
