package mathlib.graph

import mathlib.graph.properties.Edge

import scala.reflect.ClassTag

case class Graph[T](vertices: Set[Node[T]], edges: Set[Edge[Node[T]]]) {
  def +(vertex: Node[T]): Graph[T] = Graph(vertices + vertex, edges)
  def +(_vertices: Set[Node[T]]): Graph[T] = _vertices.foldLeft(this)(_ + _)
  def +(edge: Edge[Node[T]]): Graph[T] = Graph(vertices + edge.v1 + edge.v2, edges + edge)
  def +[X: ClassTag](_edges: Set[Edge[Node[T]]]): Graph[T] = _edges.foldLeft(this)(_ + _)

  def -(vertex: Node[T]): Graph[T] = Graph(vertices - vertex, edges.filter(edge => !edge.contains(vertex)))
  def -(_vertices: Set[Node[T]]): Graph[T] = _vertices.foldLeft(this)(_ - _)
  def -(edge: Edge[Node[T]]): Graph[T] = Graph(vertices, edges - edge)
  def -[X: ClassTag](_edges: Set[Edge[Node[T]]]): Graph[T] = _edges.foldLeft(this)(_ - _)

  def size: Int = vertices.size
}

case object Graph {
  def apply[T](nodes: Set[Node[T]]): Graph[T] = Graph.empty + nodes
  def apply[T, X: ClassTag](edges: Set[Edge[Node[T]]]): Graph[T] = Graph.empty + edges
  def empty[T]: Graph[T] = Graph(Set.empty, Set.empty)
}