package mathlib.graph

import mathlib.graph.properties.Edge
import mathlib.set.SetTheory._

import scala.reflect.ClassTag

case class UnTree[T](override val vertices: Set[Node[T]], root: Node[T], branches: Set[DiEdge[Node[T]]])
  extends UnweightedGraph[T,DiEdge[Node[T]]](vertices, branches) {
  override def +(vertex: Node[T]): UnTree[T] = ???

  override def +(_vertices: Set[Node[T]]): UnTree[T] = ???

  override def +(edge: DiEdge[Node[T]]): UnTree[T] = ???

  override def +[X: ClassTag](_edges: Set[DiEdge[Node[T]]]): UnTree[T] = ???

  override def -(vertex: Node[T]): UnTree[T] = ???

  override def -(_vertices: Set[Node[T]]): UnTree[T] = ???

  override def -(edge: DiEdge[Node[T]]): UnTree[T] = ???

  override def -[X: ClassTag](_edges: Set[DiEdge[Node[T]]]): UnTree[T] = ???

  override def merge[G <: Graph[T, DiEdge[Node[T]]]](that: G): UnTree[T] = that match {
    case UnTree(thatVertices: Set[Node[T]], thatRoot: Node[T], thatBranches: Set[DiEdge[Node[T]]]) =>
      ???
  }

}

case object UnTree {
  def apply[T](vertices: Set[Node[T]], root: Node[T]): UnTree[T] = UnTree.single(root) + vertices
  def apply[T, X: ClassTag](root: Node[T], edges: Set[DiEdge[Node[T]]]): UnTree[T] = UnTree.single(root) + edges
  def single[T](root: Node[T]): UnTree[T] = UnTree(Set.empty, root, Set.empty)
}