package mathlib.graph

import mathlib.graph.properties.{ProtoEdge, WeightedEdge}

class WUnDiHyperEdge[T <: Node[_]](override val nodes: Set[T], override val weight: Double) extends UnDiHyperEdge[T](nodes) with WeightedEdge with ProtoEdge[T] {

}
