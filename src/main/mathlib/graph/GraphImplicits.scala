package mathlib.graph

import mathlib.graph.hyper.{DiHyperEdge, UnDiHyperEdge, WDiHyperEdge, WUnDiHyperEdge}

object GraphImplicits {

  def N[T](value: T): Node[T] = Node(value)

  implicit class EdgeImpl[T <: Node[_]](node1: T) {
    def ~(node2: T): UnDiEdge[T] = UnDiEdge(node1, node2)
    def ~>(node2: T): DiEdge[T] = DiEdge(node1, node2)
  }

  implicit class EdgeImpl2[T](protoNode1: T) {
    def ~(protoNode2: T): UnDiEdge[Node[T]] = UnDiEdge(Node(protoNode1), Node(protoNode2))
    def ~>(protoNode2: T): DiEdge[Node[T]] = DiEdge(Node(protoNode1), Node(protoNode2))
  }

  implicit class UnDiHyperEdgeImpl1a[T <: Node[_]](unDiEdge: UnDiEdge[T]) {
    def ~(node: T): UnDiHyperEdge[T] = UnDiHyperEdge(Set(unDiEdge.left, unDiEdge.right, node))
    def %(weight: Double): WUnDiEdge[T] = WUnDiEdge(unDiEdge.left, unDiEdge.right, weight)
  }

  implicit class UnDiHyperEdgeImpl1b[T](unDiEdge: UnDiEdge[Node[T]]) {
    def ~(protoNode1: T): UnDiHyperEdge[Node[T]] = UnDiHyperEdge(Set(unDiEdge.left, unDiEdge.right, Node(protoNode1)))
  }

  implicit class UnDiHyperEdgeImpl2a[T <: Node[_]](unDiHyperEdge: UnDiHyperEdge[T]) {
    def ~(node: T): UnDiHyperEdge[T] = UnDiHyperEdge(unDiHyperEdge.nodes + node)
    def %(weight: Double): WUnDiHyperEdge[T] = new WUnDiHyperEdge(unDiHyperEdge.nodes, weight)
  }

  implicit class UnDiHyperEdgeImpl2b[T](unDiHyperEdge: UnDiHyperEdge[Node[T]]) {
    def ~(protoNode: T): UnDiHyperEdge[Node[T]] = UnDiHyperEdge(unDiHyperEdge.nodes + Node(protoNode))
  }

  implicit class DiHyperEdgeImpl1a[T <: Node[_]](diEdge: DiEdge[T]) {
    def ~(node: T): DiHyperEdge[T] = DiHyperEdge(List(diEdge.left, diEdge.right, node))
    def %(weight: Double): WDiEdge[T] = WDiEdge(diEdge.left, diEdge.right, weight)
  }

  implicit class DiHyperEdgeImpl1b[T](diEdge: DiEdge[Node[T]]) {
    def ~(protoNode: T): DiHyperEdge[Node[T]] = DiHyperEdge(List(diEdge.left, diEdge.right, Node(protoNode)))
  }

  implicit class DiHyperEdgeImpl2a[T <: Node[_]](diHyperEdge: DiHyperEdge[T]) {
    def ~(node: T): DiHyperEdge[T] = DiHyperEdge(diHyperEdge.nodes.appended(node))
    def %(weight: Double): WDiHyperEdge[T] = new WDiHyperEdge(diHyperEdge.nodes, weight)
  }

  implicit class DiHyperEdgeImpl2b[T](diHyperEdge: DiHyperEdge[Node[T]]) {
    def ~(protoNode: T): DiHyperEdge[Node[T]] = DiHyperEdge(diHyperEdge.nodes.appended(Node(protoNode)))
  }
}
