package mathlib.graph

import mathlib.graph.properties.{ProtoEdge, WeightedEdge}

class WDiHyperEdge[T <: Node[_]](override val nodes: List[T], override val weight: Double) extends DiHyperEdge[T](nodes) with WeightedEdge with ProtoEdge[T] {

}
