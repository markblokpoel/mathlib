package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge}

case class DiEdge[T <: Node[_]](override val v1: T, override val v2: T) extends Edge[T](v1, v2) with ProtoEdge[T]
