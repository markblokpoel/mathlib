package mathlib.graph.properties

import mathlib.graph.Node

abstract class HyperEdge[T <: Node[_]](edgeSet: Iterable[T]) extends ProtoEdge[T] {
  def contains(vertex: T): Boolean = edgeSet.exists(_ == vertex)
  def contains(vertex: Set[T]): Boolean = vertex.forall(v => edgeSet.exists(_ == v))
}
