package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge}

case class DiHyperEdge[T <: Node[_]](left: Set[T], right: Set[T])
    extends HyperEdge[T](left, right)
    with ProtoEdge[T]
