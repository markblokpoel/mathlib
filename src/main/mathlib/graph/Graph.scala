package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag

abstract class Graph[T, E <: Edge[Node[T]]](val vertices: Set[Node[T]], val edges: Set[E]) {
  require(
    edges.forall(e => vertices.contains(e.left) && vertices.contains(e.right)),
    "Cannot form graph, the following edges contain vertices not passed to the constructor: " + edges + vertices
//      edges
//      .filter(e => !(vertices.contains(e.left) && vertices.contains(e.right)))
//      .mkString(" ")
  )

  def +(vertex: Node[T]): Graph[T, E]

  def +(_vertices: Set[Node[T]]): Graph[T, E]

  def +(edge: E): Graph[T, E]

  def +[X: ClassTag](_edges: Set[E]): Graph[T, E]

  def -(vertex: Node[T]): Graph[T, E]

  def -(_vertices: Set[Node[T]]): Graph[T, E]

  def -(edge: E): Graph[T, E]

  def -[X: ClassTag](_edges: Set[E]): Graph[T, E]

  def merge[G <: Graph[T, E]](that: G): Graph[T, E]

  //  def hasCycles: Boolean = {
  //    def checkCycles(prev: Node[T], graph: Graph[T, Edge[Node[T]]], visited: Set[Node[T]]): Boolean = {
  //      val nextEdges = graph.edges.filter(edge => edge.v1 != prev)
  //      if(nextEdges.isEmpty) true
  //      else if(nextEdges.exists(edge => visited contains edge.v2)) false
  //      else nextEdges.forall(edge => checkCycles(edge.v2, graph - edge, visited + prev))
  //    }
  //    val randomVertex = this.vertices.random
  //    if(randomVertex.isEmpty) true
  //    else checkCycles(randomVertex.get, Graph(), Set.empty)
  //  }


  def calcAdjacencyList(): Map[Node[T], Set[Node[T]]]

  lazy val adjacencyList: Map[Node[T], Set[Node[T]]] = calcAdjacencyList()

  @tailrec
  private def nthAdjacencyListRec(n: Int, adjL: Map[Node[T], Set[Node[T]]]): Map[Node[T], Set[Node[T]]] = {
    if(n == 0) adjL
    else {
      nthAdjacencyListRec(
        n - 1,
        adjL.map(kv => {
          val v = kv._1
          val adj = kv._2
          v -> adj.flatMap(a => adjacencyList(a) - v)
        })
      )
    }
  }

  def nthAdjacencyList(n: Int): Map[Node[T], Set[Node[T]]] = {
    nthAdjacencyListRec(n, adjacencyList)
  }

  def size: Int = vertices.size
}

//case object Graph {
//  def apply[T](nodes: Set[Node[T]]): Graph[T] = Graph.empty + nodes
//  def apply[T, X: ClassTag](edges: Set[Edge[Node[T]]]): Graph[T] = Graph.empty + edges
//  def empty[T]: Graph[T] = Graph(Set.empty, Set.empty)
//}
