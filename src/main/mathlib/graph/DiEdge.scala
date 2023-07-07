package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge}

case class DiEdge[T <: Node[_]](override val left: T, override val right: T)
    extends Edge[T](left, right)
    with ProtoEdge[T] {
  override def getNeighborOf(vertex: T): Option[T] =
    if(left == vertex) Some(right)
    else None
}