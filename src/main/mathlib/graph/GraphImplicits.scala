package mathlib.graph

import mathlib.graph.hyper.{DiHyperEdge, UnDiHyperEdge, WDiHyperEdge, WUnDiHyperEdge}

object GraphImplicits {

  def N[T](value: T): Node[T] = Node(value)

  implicit class EdgeImpl[T <: Node[_]](node1: T) {
    def ~(node2: T): UnDiEdge[T] = UnDiEdge(node1, node2)
    def ~>(node2: T): DiEdge[T]  = DiEdge(node1, node2)
  }

  implicit class EdgeImpl2[T](protoNode1: T) {
    def ~(protoNode2: T): UnDiEdge[Node[T]] = UnDiEdge(Node(protoNode1), Node(protoNode2))
    def ~>(protoNode2: T): DiEdge[Node[T]]  = DiEdge(Node(protoNode1), Node(protoNode2))
  }

  implicit class WDiEdgeImpl[T <: Node[_]](diEdge: DiEdge[T]) {
    def %(weight: Double): WDiEdge[T] = WDiEdge(diEdge.left, diEdge.right, weight)
  }

  implicit class WUnDiEdgeImpl[T <: Node[_]](unDiEdge: UnDiEdge[T]) {
    def %(weight: Double): WUnDiEdge[T] = WUnDiEdge(unDiEdge.left, unDiEdge.right, weight)
  }


  implicit class DiHyperEdgeImpl1[T](protoNodeSet1: Set[T]) {
    def ~>(protoNodeSet2: Set[T]): DiHyperEdge[Node[T]] =
      DiHyperEdge(protoNodeSet1.map(N), protoNodeSet2.map(N))
  }

  implicit class DiHyperEdgeImpl2[T](nodeSet1: Set[Node[T]]) {
    def ~>(nodeSet2: Set[Node[T]]): DiHyperEdge[Node[T]] =
      DiHyperEdge(nodeSet1, nodeSet2)
  }

  implicit class WDiHyperEdgeImpl[T](diHyperEdge: DiHyperEdge[Node[T]]) {
    def %(weight: Double): WDiHyperEdge[Node[T]] = WDiHyperEdge(diHyperEdge.left, diHyperEdge.right, weight)
  }

  implicit class UnDiHyperEdgeImpl1[T](protoNodeSet1: Set[T]) {
    def ~(protoNodeSet2: Set[T]): UnDiHyperEdge[Node[T]] =
      UnDiHyperEdge(protoNodeSet1.map(N), protoNodeSet2.map(N))
  }

  implicit class UnDiHyperEdgeImpl2[T](nodeSet1: Set[Node[T]]) {
    def ~(nodeSet2: Set[Node[T]]): UnDiHyperEdge[Node[T]] =
      UnDiHyperEdge(nodeSet1, nodeSet2)
  }

  implicit class WUnDiHyperEdgeImpl[T](diHyperEdge: UnDiHyperEdge[Node[T]]) {
    def %(weight: Double): WUnDiHyperEdge[Node[T]] = WUnDiHyperEdge(diHyperEdge.left, diHyperEdge.right, weight)
  }


  //  implicit class UnDiHyperEdgeImpl1a[T <: Node[_]](unDiEdge: UnDiEdge[T]) {
////    def ~(node: T): UnDiHyperEdge[T] = UnDiHyperEdge(Set(unDiEdge.left, unDiEdge.right, node))
//  }
//
//  implicit class UnDiHyperEdgeImpl1b[T](unDiEdge: UnDiEdge[Node[T]]) {
//    def ~(protoNode1: T): UnDiHyperEdge[Node[T]] = UnDiHyperEdge(
//      Set(unDiEdge.left, unDiEdge.right, Node(protoNode1))
//    )
//  }
//
//  implicit class UnDiHyperEdgeImpl2a[T <: Node[_]](unDiHyperEdge: UnDiHyperEdge[T]) {
////    def ~(node: T): UnDiHyperEdge[T] = UnDiHyperEdge(unDiHyperEdge.nodes + node)
//    def %(weight: Double): WUnDiHyperEdge[T] =
//      new WUnDiHyperEdge(unDiHyperEdge.left, unDiHyperEdge.right, weight)
//  }

//  implicit class UnDiHyperEdgeImpl2b[T](unDiHyperEdge: UnDiHyperEdge[Node[T]]) {
////    def ~(protoNode: T): UnDiHyperEdge[Node[T]] = UnDiHyperEdge(unDiHyperEdge.left, unDiHyperEdge.right + Node(protoNode))
//  }




  implicit class DiHyperEdgeImpl2a[T <: Node[_]](diHyperEdge: DiHyperEdge[T]) {
    def %(weight: Double): WDiHyperEdge[T] =
      new WDiHyperEdge(diHyperEdge.left, diHyperEdge.right, weight)
  }


}
