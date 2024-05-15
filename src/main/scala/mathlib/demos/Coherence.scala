package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph.{DiGraph, Node, WDiGraph, WUnDiEdge, WUnDiGraph}
import mathlib.set.SetTheory._

object Coherence {

  def coherence(
      network: WUnDiGraph[String],
      positiveConstraints: Set[WUnDiEdge[Node[String]]]
  ): Map[Node[String], Boolean] = {
    val negativeConstraints: Set[WUnDiEdge[Node[String]]] =
      network.edges \ positiveConstraints

    def cohPlus(assignment: Map[Node[String], Boolean]): Double = {
      def isSatisfied(pc: WUnDiEdge[Node[String]]): Double =
        if (assignment(pc.left) == assignment(pc.right)) pc.weight
        else 0.0

      sum(positiveConstraints, isSatisfied _)
    }

    def cohMinus(assignment: Map[Node[String], Boolean]): Double = {
      def isSatisfied(pc: WUnDiEdge[Node[String]]): Double =
        if (assignment(pc.left) != assignment(pc.right)) pc.weight
        else 0.0

      sum(negativeConstraints, isSatisfied _)
    }

    def coh(assignment: Map[Node[String], Boolean]): Double =
      cohPlus(assignment) + cohMinus(assignment)

    val allPossibleTruthValueAssignments =
      network.vertices.allMappings(Set(true, false))
    val optimalSolutions =
      argMax(allPossibleTruthValueAssignments, coh)
    optimalSolutions.random.get
  }

  def main(args: Array[String]): Unit = {

    val positiveConstraints = Set(
      N("a") ~ N("b") % 1,
      N("a") ~ N("c") % 1.2,
      N("b") ~ N("d") % 0.3,
      N("d") ~ N("b") % 0.3
    )
    val negativeConstraints = Set(
      N("b") ~ N("c") % 0.6,
      N("a") ~ N("d") % 1.6
    )

    val network = WUnDiGraph.empty + positiveConstraints + negativeConstraints

    println("Small example")
    coherence(network, positiveConstraints).foreach(println)

    val size                      = 10
    val numberOfEdges             = 15
    val randomNetwork             = WUnDiGraph.uniform(size, numberOfEdges)
    val randomPositiveConstraints = randomNetwork.edges.splitAt(randomNetwork.edges.size / 2)._1
    println("Random network example")
    coherence(randomNetwork, randomPositiveConstraints).foreach(println)
  }
}
