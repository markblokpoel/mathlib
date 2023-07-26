package mathlib.graph

import mathlib.graph.hyper.{DiHyperEdge, UnDiHyperEdge, WDiHyperEdge, WUnDiHyperEdge}

/** A collection of implicit functions to construct nodes, edges and graphs. */
object GraphImplicits {

  /** Creates a Node (representing a vertex) from value of type T, wrapping the value with [[Node]].
    * @param value
    *   The value of the node.
    * @tparam T
    *   The type of the node.
    * @return
    *   The node.
    */
  def N[T](value: T): Node[T] = Node(value)

  /** Implicit functions to create edges from nodes (vertices).
    * @param node1
    *   The left vertex.
    * @tparam T
    *   The type of the vertices and edge.
    */
  implicit class EdgeImpl[T <: Node[_]](node1: T) {

    /** Creates an undirected edge between node1 and node2.
      * @param node2
      *   The right vertex.
      * @return
      *   An undirected edge.
      */
    def ~(node2: T): UnDiEdge[T] = UnDiEdge(node1, node2)

    /** Creates a directed edge between node1 and node2.
      * @param node2
      *   The right vertex.
      * @return
      *   A directed edge.
      */
    def ~>(node2: T): DiEdge[T] = DiEdge(node1, node2)
  }

  /** Implicit functions to create edges from basic types.
    * @param protoNode1
    *   The basic type representing the left vertex to be wrapped in [[Node]].
    * @tparam T
    *   The type of the vertices and edge.
    */
  implicit class EdgeImpl2[T](protoNode1: T) {

    /** Creates an undirected edge between node1 and node2.
      * @param protoNode2
      *   The basic type representing the right vertex to be wrapped in [[Node]].
      * @return
      *   An undirected edge.
      */
    def ~(protoNode2: T): UnDiEdge[Node[T]] = UnDiEdge(Node(protoNode1), Node(protoNode2))

    /** Creates a directed edge between node1 and node2.
      * @param protoNode2
      *   The basic type representing the right vertex to be wrapped in [[Node]].
      * @return
      *   A directed edge.
      */
    def ~>(protoNode2: T): DiEdge[Node[T]] = DiEdge(Node(protoNode1), Node(protoNode2))
  }

  /** Implicit function to create a weighted directed edge from a base (unweighted) directed edge.
    * @param diEdge
    *   The directed edge serving as the base.
    * @tparam T
    *   The type of the edge.
    */
  implicit class WDiEdgeImpl[T <: Node[_]](diEdge: DiEdge[T]) {

    /** Creates a weighted directed edge from a directed edge.
      * @param weight
      *   The weight of the edge.
      * @return
      *   A weighted directed edge of type T.
      */
    def %(weight: Double): WDiEdge[T] = WDiEdge(diEdge.left, diEdge.right, weight)
  }

  /** Implicit function to create a weighted undirected edge from a base (unweighted) undirected
    * edge.
    * @param unDiEdge
    *   The undirected edge serving as the base.
    * @tparam T
    *   The type of the edge.
    */
  implicit class WUnDiEdgeImpl[T <: Node[_]](unDiEdge: UnDiEdge[T]) {

    /** Creates a weighted undirected edge from an (unweighted) undirected edge.
      * @param weight
      *   The weight of the edge.
      * @return
      *   A weighted undirected edge of type T.
      */
    def %(weight: Double): WUnDiEdge[T] = WUnDiEdge(unDiEdge.left, unDiEdge.right, weight)
  }

  /** Implicit function to create a directed hyper edge between two sets of base types representing
    * vertices.
    * @param protoNodeSet1
    *   The set of basic types representing the left side of the hyper edge, each element in the set
    *   will be wrapped in [[Node]].
    * @tparam T
    *   The type of the directed hyper edge.
    */
  implicit class DiHyperEdgeImpl1[T](protoNodeSet1: Set[T]) {

    /** Creates a directed hyper edge between protoNodeSet1 and protoNodeSet2.
      * @param protoNodeSet2
      *   The set of basic types representing the right side of the hyper edge, each element in the
      *   set will be wrapped in [[Node]].
      * @return
      *   A directed hyper edge.
      */
    def ~>(protoNodeSet2: Set[T]): DiHyperEdge[Node[T]] =
      DiHyperEdge(protoNodeSet1.map(N), protoNodeSet2.map(N))
  }

  /** Implicit function to create a directed hyper edge between two sets of nodes representing
    * vertices.
    * @param nodeSet1
    *   The set of nodes representing the left side of the hyper edge.
    * @tparam T
    *   The type of the directed hyper edge.
    */
  implicit class DiHyperEdgeImpl2[T](nodeSet1: Set[Node[T]]) {

    /** Creates a directed hyper edge between nodeSet1 and nodeSet2.
      * @param nodeSet2
      *   The set of nodes representing the right side of the hyper edge.
      * @return
      *   A directed hyper edge.
      */
    def ~>(nodeSet2: Set[Node[T]]): DiHyperEdge[Node[T]] =
      DiHyperEdge(nodeSet1, nodeSet2)
  }

  /** Implicit function to create a weighted directed hyper edge from a directed hyper edge.
    * @param diHyperEdge
    *   The directed hyper edge serving as the base for the weighted directed hyper edge.
    * @tparam T
    *   The type of the weighted directed hyper edge.
    */
  implicit class WDiHyperEdgeImpl[T](diHyperEdge: DiHyperEdge[Node[T]]) {

    /** Creates a weighted directed hyper edge from an (unweighted) directed hyper edge.
      * @param weight
      *   The weight of the hyper edge.
      * @return
      *   A weighted directed hyper edge of type T.
      */
    def %(weight: Double): WDiHyperEdge[Node[T]] =
      WDiHyperEdge(diHyperEdge.left, diHyperEdge.right, weight)
  }

  /** Implicit function to create an undirected hyper edge between two sets of base types
    * representing vertices.
    * @param protoNodeSet1
    *   The set of basic types representing the left side of the hyper edge, each element in the set
    *   will be wrapped in [[Node]].
    * @tparam T
    *   The type of the undirected hyper edge.
    */
  implicit class UnDiHyperEdgeImpl1[T](protoNodeSet1: Set[T]) {

    /** Creates an undirected hyper edge between protoNodeSet1 and protoNodeSet2.
      * @param protoNodeSet2
      *   The set of basic types representing the right side of the hyper edge, each element in the
      *   set will be wrapped in [[Node]].
      * @return
      *   An undirected hyper edge.
      */
    def ~(protoNodeSet2: Set[T]): UnDiHyperEdge[Node[T]] =
      UnDiHyperEdge(protoNodeSet1.map(N), protoNodeSet2.map(N))
  }

  /** Implicit function to create an undirected hyper edge between two sets of nodes representing
    * vertices.
    * @param nodeSet1
    *   The set of nodes representing the left side of the hyper edge.
    * @tparam T
    *   The type of the undirected hyper edge.
    */
  implicit class UnDiHyperEdgeImpl2[T](nodeSet1: Set[Node[T]]) {

    /** Creates an undirected hyper edge between nodeSet1 and nodeSet2.
      * @param nodeSet2
      *   The set of nodes representing the right side of the hyper edge.
      * @return
      *   An undirected hyper edge.
      */
    def ~(nodeSet2: Set[Node[T]]): UnDiHyperEdge[Node[T]] =
      UnDiHyperEdge(nodeSet1, nodeSet2)
  }

  /** Implicit function to create a weighted undirected hyper edge from an undirected hyper edge.
    * @param unDiHyperEdge
    *   The undirected hyper edge serving as the base for the weighted undirected hyper edge.
    * @tparam T
    *   The type of the weighted undirected hyper edge.
    */
  implicit class WUnDiHyperEdgeImpl[T](unDiHyperEdge: UnDiHyperEdge[Node[T]]) {

    /** Creates a weighted undirected hyper edge from an (unweighted) undirected hyper edge.
      * @param weight
      *   The weight of the hyper edge.
      * @return
      *   A weighted undirected hyper edge of type T.
      */
    def %(weight: Double): WUnDiHyperEdge[Node[T]] =
      WUnDiHyperEdge(unDiHyperEdge.left, unDiHyperEdge.right, weight)
  }
}
