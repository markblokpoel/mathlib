package mathlib.demos

import mathlib.graph.{Graph, Node}
import mathlib.set.SetTheory._

object VertexCover {

  // Example of bounded exhaustive search
  def vertexCover(graph: Graph[String], k: Int): Set[Set[Node[String]]] = {
    def isVC(subset: Set[Node[String]]): Boolean =
      graph.edges.forall(edge => subset.contains(edge.v1) || subset.contains(edge.v2))

    powersetUp(graph.vertices, k)
      .build(isVC)
  }

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
  }

  // Bounded search tree, using generic class
  def fptvc(graph: Graph[String], k: Int, acc: Set[Node[String]]): Set[Set[Node[String]]] = {
    def branch(graph: Graph[String]): Set[(Graph[String], Node[String])] = {
      val edge = graph.edges.random
      if(edge.isEmpty) Set.empty
      else {
        val left = edge.get.v1
        val right = edge.get.v2
        val leftGraph = graph - left
        val rightGraph = graph - right

        Set((leftGraph, left), (rightGraph, right))
      }
    }

    BoundedSearchTree(k, branch).search(graph)
  }

  // Bounded search tree, specific implementation
  def fptVertexCover(graph: Graph[String], k: Int, acc: Set[Node[String]]): Set[Set[Node[String]]] = {
    val edge = graph.edges.random

    val left = edge.get.v1
    val right = edge.get.v2

    val leftGraph = graph - left
    val rightGraph = graph - right

    val leftSolutions =
      if(acc.size + 1 < k && leftGraph.edges.nonEmpty) fptVertexCover(leftGraph, k, acc + left)
      else if(leftGraph.edges.isEmpty) Set(acc + left)
      else Set.empty[Set[Node[String]]]

    val rightSolutions =
      if(acc.size + 1 < k && rightGraph.edges.nonEmpty) fptVertexCover(rightGraph, k, acc + right)
      else if(rightGraph.edges.isEmpty) Set(acc + right)
      else Set.empty[Set[Node[String]]]

    leftSolutions ++ rightSolutions
  }

}
