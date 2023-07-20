package mathlib.graph

import mathlib.graph.properties.Edge

import scala.annotation.tailrec


abstract class UnweightedGraph[T, E <: Edge[Node[T]]](override val vertices: Set[Node[T]], override val edges: Set[E])
  extends Graph[T, E](vertices, edges) {

  def calcAdjacencyList(): Map[Node[T], Set[Node[T]]] =
    vertices.map(v => {
      val adjacents = edges
        .filter(_ contains v)
        .map(e => {
          val adjacentOption = e.getNeighborOf(v)
          if (adjacentOption.isDefined) Some(adjacentOption.get)
          else None
        })
      v -> adjacents.filter(_.isDefined).map(_.get)
    }).toMap

  lazy val adjacencyList: Map[Node[T], Set[Node[T]]] = calcAdjacencyList()

  @tailrec
  private def nthAdjacencyListRec(n: Int, adjL: Map[Node[T], Set[Node[T]]]): Map[Node[T], Set[Node[T]]] = {
    if (n == 0) adjL
    else {
      nthAdjacencyListRec(
        n - 1,
        adjL.map(kv => {
          val v = kv._1
          val adj: Set[Node[T]] = kv._2
          v -> adj.flatMap(a => adjacencyList(a))
        })
      )
    }
  }

  def nthAdjacencyList(n: Int): Map[Node[T], Set[Node[T]]] = {
    nthAdjacencyListRec(n, adjacencyList)
  }

  override def containsCycle: Boolean = ???
}
