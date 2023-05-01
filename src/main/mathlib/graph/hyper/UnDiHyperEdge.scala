package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge}

case class UnDiHyperEdge[T <: Node[_]](nodes: Set[T]) extends HyperEdge[T](nodes) with ProtoEdge[T] {

}
