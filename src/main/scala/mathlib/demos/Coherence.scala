package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph.{DiGraph, Node, WDiGraph, WUnDiEdge, WUnDiGraph}
import mathlib.set.SetTheory._
import scala.collection.parallel.CollectionConverters._

object Coherence {

  def main(args: Array[String]): Unit = {

    def isPositiveConstraintSatisfied(
        positiveConstraint: WUnDiEdge[Node[String]],
        assignment: Map[Node[String], Boolean]
    ): Boolean = assignment(positiveConstraint.left) == assignment(positiveConstraint.right)

    def isNegativeConstraintSatisfied(
        negativeConstraint: WUnDiEdge[Node[String]],
        assignment: Map[Node[String], Boolean]
    ): Boolean = assignment(negativeConstraint.left) != assignment(negativeConstraint.right)

    def cohPlus(
        assignment: Map[Node[String], Boolean],
        positiveConstraints: Set[WUnDiEdge[Node[String]]]
    ): Double =
      positiveConstraints.toList
        .map((pc: WUnDiEdge[Node[String]]) => {
          if (isPositiveConstraintSatisfied(pc, assignment)) pc.weight
          else 0.0
        })
        .sum

    def cohMinus(
        assignment: Map[Node[String], Boolean],
        negativeConstraints: Set[WUnDiEdge[Node[String]]]
    ): Double =
      negativeConstraints.toList
        .map((nc: WUnDiEdge[Node[String]]) => {
          if (isPositiveConstraintSatisfied(nc, assignment)) nc.weight
          else 0.0
        })
        .sum

    def coh(
        assignment: Map[Node[String], Boolean],
        positiveConstraints: Set[WUnDiEdge[Node[String]]],
        negativeConstraints: Set[WUnDiEdge[Node[String]]]
    ): Double =
      cohPlus(assignment, positiveConstraints) + cohMinus(assignment, negativeConstraints)

    def cMinusCoherence(
        network: WUnDiGraph[String],
        positiveConstraints: Set[WUnDiEdge[Node[String]]],
        negativeConstraints: Set[WUnDiEdge[Node[String]]]
    ): Set[Map[Node[String], Boolean]] = {
      require(positiveConstraints \/ negativeConstraints == network.edges, "C+ union C- != E")
      require(
        positiveConstraints /\ negativeConstraints == Set.empty,
        "C+ intersect C- is not empty"
      )

      def completeBeliefAssignment(
          negativeAssignments: Map[Node[String], Boolean]
      ): Map[Node[String], Boolean] = {
        val positiveAssignments = positiveConstraints
          .flatMap(pc => {
            if (negativeAssignments.contains(pc.left) && !negativeAssignments.contains(pc.right))
              Set(pc.right -> negativeAssignments(pc.left))
            else if (
              !negativeAssignments.contains(pc.left) && negativeAssignments.contains(pc.right)
            ) Set(pc.left -> negativeAssignments(pc.right))
            else if (
              !negativeAssignments.contains(pc.left) && !negativeAssignments.contains(pc.right)
            ) Set(pc.left -> true, pc.right -> true)
            else Set.empty
          })
          .toMap
        positiveAssignments ++ negativeAssignments
      }

      negativeConstraints
        .flatMap(nc => Set(nc.left, nc.right))  // Get all vertices connected to negative constraints
        .allMappings(Set(true, false))          // Generate all possible truth value assignments
        .par
        .map(completeBeliefAssignment)          // For each partial assignment (only negative constraints), complete
                                                // the assignment for the whole network
        .toList.toSet
        .argMax(coh(_, positiveConstraints, negativeConstraints))   // Find the value assignments with maximal coherence
    }

    def coherence(
        network: WUnDiGraph[String],
        positiveConstraints: Set[WUnDiEdge[Node[String]]],
        negativeConstraints: Set[WUnDiEdge[Node[String]]]
    ): Set[Map[Node[String], Boolean]] = {
      require(positiveConstraints \/ negativeConstraints == network.edges, "C+ union C- != E")
      require(
        positiveConstraints /\ negativeConstraints == Set.empty,
        "C+ intersect C- is not empty"
      )

      network.vertices
        .allMappings(Set(true, false))
        .argMax(coh(_, positiveConstraints, negativeConstraints))
    }

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

    println("Exhaustive Coherence")
    coherence(network, positiveConstraints, negativeConstraints).foreach(println)
    println("C- Coherence (FPT)")
    cMinusCoherence(network, positiveConstraints, negativeConstraints).foreach(println)

    val size = 50
    val edgeProbability = 0.5
    val largeNetwork = WUnDiGraph.random(size, edgeProbability)


    println(s"#vertices:\t\t\t\t$size")
    println(s"#edges:\t\t\t\t\t${largeNetwork.edges.size} ≈ $size * $size * $edgeProbability = ${size*size*edgeProbability}")

    val (lpc, lnc) =
      largeNetwork.edges.splitAt(largeNetwork.edges.size - 6)

    println(s"#positive constraints:\t${lpc.size}")
    println(s"#negative constraints:\t${lnc.size}")

    cMinusCoherence(largeNetwork, lpc, lnc).foreach(println)


  }
}
