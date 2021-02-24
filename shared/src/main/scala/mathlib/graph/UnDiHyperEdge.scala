package mathlib.graph

import mathlib.graph.properties.{HyperEdge, ProtoEdge}

case class UnDiHyperEdge[T <: Node[_]](nodes: Set[T]) extends HyperEdge[T](nodes) with ProtoEdge[T] {

}
