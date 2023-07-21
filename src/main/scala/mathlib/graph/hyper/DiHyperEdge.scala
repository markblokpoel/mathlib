package mathlib.graph.hyper

import mathlib.graph.Node
import mathlib.graph.properties.{HyperEdge, ProtoEdge}

/** Represents a directed hyper edge.
 * @param left
 *   The left side of the hyper edge, namely a list of vertices.
 * @param right
 *   The right side of the hyper edge, namely a list of vertices.
 * @tparam T
 *   The type of the vertices that belong to this edge.
 */
case class DiHyperEdge[T <: Node[_]](left: Set[T], right: Set[T])
    extends HyperEdge[T](left, right)
    with ProtoEdge[T]
