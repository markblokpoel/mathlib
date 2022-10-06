package mathlib.graph.properties

import mathlib.graph.Node

abstract class HyperEdge[T <: Node[_]](edgeSet: Iterable[T]) extends ProtoEdge[T] {

}
