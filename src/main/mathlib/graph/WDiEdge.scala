package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge, WeightedEdge}

case class WDiEdge[T <: Node[_]](override val v1: T, override val v2: T, weight: Double) extends Edge[T](v1, v2) with WeightedEdge with ProtoEdge[T]
