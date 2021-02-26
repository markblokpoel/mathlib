package mathlib.graph

import mathlib.set.SetTheory._

class UnTree[T](vertices: Set[Node[T]], root: Node[T], branches: Set[DiEdge[Node[T]]])
  extends Graph[T,DiEdge[Node[T]]](vertices, branches) {



  override def merge(that: Graph[T, DiEdge[Node[T]]]): Graph[T, DiEdge[Node[T]]] = {
    val mergedGraph = super.merge(that)
???
  }

}
