package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplSet

import scala.reflect.ClassTag

abstract class Graph[T, E <: Edge[Node[T]]](val vertices: Set[Node[T]], val edges: Set[E]) {

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

  private lazy val leftNeighbours: Map[E, Set[E]] = edges.map(edge =>
    edge -> (edges - edge).filter(_.contains(edge.left))
  ).toMap

  def nextLeft(edge: E): Set[E] = leftNeighbours(edge)

  private lazy val rightNeighbours: Map[E, Set[E]] = edges.map(edge =>
    edge -> (edges - edge).filter(_.contains(edge.right))
  ).toMap

  def nextRight(edge: E): Set[E] = rightNeighbours(edge)


  def size: Int = vertices.size
}

//case object Graph {
//  def apply[T](nodes: Set[Node[T]]): Graph[T] = Graph.empty + nodes
//  def apply[T, X: ClassTag](edges: Set[Edge[Node[T]]]): Graph[T] = Graph.empty + edges
//  def empty[T]: Graph[T] = Graph(Set.empty, Set.empty)
//}