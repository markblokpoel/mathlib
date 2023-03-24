package mathlib.graph.properties

import mathlib.graph.{Graph, Node}

abstract class Edge[T <: Node[_]](val v1: T, val v2: T) extends ProtoEdge[T] {
  def contains(vertex: T): Boolean = v1 == vertex || v2 == vertex
}
