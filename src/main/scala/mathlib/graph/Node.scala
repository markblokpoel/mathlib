package mathlib.graph



/** Case class to represent a vertex or node in graphs. Contains a label/value of type T.
 * @param value The value this vertex contains, also used to uniquely identify the vertex.
 * @tparam T The type of the label.
 */
case class Node[T](value: T) {
  /**
   * Label for backwards compatibility.
   * @return
   */
  def label: T = value
}
