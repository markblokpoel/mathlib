package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge, WeightedEdge}

case class WDiHyperEdge[T <: Node[_]](
    left: Set[T],
    right: Set[T],
    override val weight: Double
) extends HyperEdge[T](left, right)
    with WeightedEdge
