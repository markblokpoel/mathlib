package mathlib.graph

/** Case class to represent a vertex or node in graphs. Contains a label of type T.
 * @param label The label to identify the vertex or node with.
 * @tparam T The type of the label.
 */
case class Node[T](label: T)
