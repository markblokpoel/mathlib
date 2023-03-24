package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge, WeightedEdge}

case class WDiEdge[T <: Node[_]](override val left: T, override val right: T, weight: Double) extends Edge[T](left, right) with WeightedEdge with ProtoEdge[T]
