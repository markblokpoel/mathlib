package mathlib.graph.properties

import mathlib.graph.{Graph, Node}

abstract class Edge[T <: Node[_]](val left: T, val right: T) extends ProtoEdge[T] {
  def contains(vertex: T): Boolean = left == vertex || right == vertex

  def getNeighborOf(vertex: T): Option[T] =
    if(left == vertex) Some(right)
    else Some(left)
}
