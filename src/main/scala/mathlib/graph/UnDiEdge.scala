package mathlib.graph

import mathlib.graph.properties.{Edge, ProtoEdge}

/** Represents an undirected edge.
  * @param left
  *   Left vertex of the edge.
  * @param right
  *   Right vertex of the edge.
  * @tparam T
  *   The type of the vertices.
  */
case class UnDiEdge[T <: Node[_]](override val left: T, override val right: T)
    extends Edge[T](left, right)
    with ProtoEdge[T] {

  /** Checks if this instance can equal that instance.
    * @param that
    *   The instance to check.
    * @return
    *   ```true``` if ```that``` is of type [[UnDiEdge]]
    */
  override def canEqual(that: Any): Boolean = that.isInstanceOf[UnDiEdge[_]]

  /** Checks if this equals that.
    *
    * Undirected edges (x,y) equal (y,x). This equals function tests equivalence respecting this
    * property.
    * @param that
    *   The object to test equivalence to.
    * @return
    *   ```true``` if that.left == that.left && that.right == that.right or that.left == that.right
    *   && that.right == that.left.
    */
  override def equals(that: Any): Boolean =
    that match {
      case that: UnDiEdge[_] =>
        left == that.left && right == that.right ||
        left == that.right && right == that.left
      case _ => false
    }

  /** Calculates the hash code of the edge.
    * @return
    *   Hash code of the edge.
    */
  override def hashCode(): Int = {
    val prime = 1693
    (prime + left.hashCode()) * prime + right.hashCode() +
      (prime + right.hashCode()) * prime + left.hashCode()
  }
}
