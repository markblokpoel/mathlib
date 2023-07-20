package mathlib.graph.properties

import mathlib.graph.Node

abstract class HyperEdge[T <: Node[_]](left: Iterable[T], right: Iterable[T]) extends ProtoEdge[T] {
  def contains(vertex: T): Boolean =
    left.exists(_ == vertex) || right.exists(_ == vertex)
  def contains(vertex: Set[T]): Boolean =
    vertex.forall(v => left.exists(_ == v)) || vertex.forall(v => right.exists(_ == v))
}
