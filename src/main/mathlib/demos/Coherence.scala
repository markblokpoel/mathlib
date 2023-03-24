package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph.{DiGraph, WUnDiGraph}

object Coherence {

  def main(args: Array[String]): Unit = {

    val network = DiGraph.empty +
      N("A") ~> N("B")  +
      N("A") ~> N("C")  +
      N("B") ~> N("C") +
      N("C") ~> N("D")  +
      N("C") ~> N("E") +
      N("C") ~> N("F")

    println(network)

    val e1 = N("A") ~> N("B")

    println(network.nextLeft(e1))
    println(network.nextRight(e1))
  }
}
