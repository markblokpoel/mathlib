package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory.ImplSet

import scala.annotation.tailrec
import scala.reflect.ClassTag

abstract class Graph[T, E <: Edge[Node[T]]](val vertices: Set[Node[T]], val edges: Set[E]) {
  require(
    edges.forall(e => vertices.contains(e.left) && vertices.contains(e.right)),
    "Cannot form graph, the following edges contain vertices not passed to the constructor: " + edges + vertices
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

  def size: Int = vertices.size
  def containsCycle: Boolean
  def isEmpty: Boolean = vertices.isEmpty
  def noEdges: Boolean = edges.isEmpty


}

//case object Graph {
//  def apply[T](nodes: Set[Node[T]]): Graph[T] = Graph.empty + nodes
//  def apply[T, X: ClassTag](edges: Set[Edge[Node[T]]]): Graph[T] = Graph.empty + edges
//  def empty[T]: Graph[T] = Graph(Set.empty, Set.empty)
//}
