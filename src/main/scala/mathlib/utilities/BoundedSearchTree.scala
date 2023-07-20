package mathlib.utilities

import mathlib.graph.{Graph, Node}


case class BoundedSearchTree[T, S](bound: Int, branch: T => Set[(T, S)]) {
  def search(state: T, depth: Int = 0, acc: Set[S] = Set.empty): Set[Set[S]] = {
    if(depth > bound) Set.empty
    else {
      val branches = branch(state)
      if(branches.isEmpty) Set(acc)
      else {
        branches.flatMap(branch => {
          val nextState = branch._1
          val solutionPart = branch._2
          search(nextState, depth + 1, acc + solutionPart)
        })
      }
    }
  }

//  def constructFullTree(state: T, depth: Int = 0, acc: Set[S] = Set.empty): Graph[(T, Set[S])] = {
//    val currentNode = Node((state, acc))
//    if(depth > bound) Graph(nodes = Set(Node((state, Set.empty[Set[S]]))))
//    else {
//      val branches = branch(state)
//      if(branches.isEmpty) Graph(nodes = Set(currentNode))
//      else {
//        branches
//          .map(branch => {
//            val nextState = branch._1
//            val solutionPart = branch._2
//            constructFullTree(nextState, depth + 1, acc + solutionPart)
//          })
//          .foldLeft(Graph(Set(Node((state, acc)))))(_ merge _)
//      }
//    }
//  }
}