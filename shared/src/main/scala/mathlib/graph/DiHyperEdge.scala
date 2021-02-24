package mathlib.graph

import mathlib.graph.properties.{HyperEdge, ProtoEdge}

case class DiHyperEdge[T <: Node[_]](nodes: List[T]) extends HyperEdge[T](nodes) with ProtoEdge[T] {

}
