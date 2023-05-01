package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.HyperEdge

import scala.reflect.ClassTag

abstract class HyperGraph[T, E <: HyperEdge[Node[T]]](val vertices: Set[Node[T]], val edges: Set[E]) {
  def +(vertex: Node[T]): HyperGraph[T, E]

  def +(_vertices: Set[Node[T]]): HyperGraph[T, E]

  def +(edge: E): HyperGraph[T, E]

  def +[X: ClassTag](_edges: Set[E]): HyperGraph[T, E]

  def -(vertex: Node[T]): HyperGraph[T, E]

  def -(_vertices: Set[Node[T]]): HyperGraph[T, E]

  def -(edge: E): HyperGraph[T, E]

  def -[X: ClassTag](_edges: Set[E]): HyperGraph[T, E]

  def merge[G <: HyperGraph[T, E]](that: G): HyperGraph[T, E]

  def size: Int = vertices.size
}
