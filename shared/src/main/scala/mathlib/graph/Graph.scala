package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplSet

import scala.reflect.ClassTag

abstract class Graph[T, E <: Edge[Node[T]]](vertices: Set[Node[T]], edges: Set[E]) {
  def +(vertex: Node[T]): Graph[T,E]
  def +(_vertices: Set[Node[T]]): Graph[T,E]
  def +(edge: E): Graph[T,E]
  def +[X: ClassTag](_edges: Set[E]): Graph[T,E]
  def -(vertex: Node[T]): Graph[T,E]
  def -(_vertices: Set[Node[T]]): Graph[T,E]
  def -(edge: Edge[Node[T]]): Graph[T,E]
  def -[X: ClassTag](_edges: Set[E]): Graph[T,E]
  def merge(that: Graph[T,E]): Graph[T,E]

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

  def size: Int = vertices.size
}

//case object Graph {
//  def apply[T, E >: Edge[Node[T]]](nodes: Set[Node[T]]): Graph[T,E] = Graph.empty[T,E] + nodes
//  def apply[T, E >: Edge[Node[T]], X: ClassTag](edges: Set[Edge[Node[T]]]): Graph[T,E] = Graph.empty[T,E] + edges
//  def empty[T, E >: Edge[Node[T]]]: Graph[T,E] = Graph(Set.empty, Set.empty)
//}