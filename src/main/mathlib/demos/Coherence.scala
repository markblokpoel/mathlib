package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph.WUnDiGraph

object Coherence {

  def main(args: Array[String]): Unit = {

    val network = WUnDiGraph.empty +
      N("A") ~ N("B") % 1.0 +
      N("A") ~ N("C") % 1.0 +
      N("B") ~ N("C") % 1.0 +
      N("C") ~ N("D") % 1.0 +
      N("C") ~ N("E") % 1.0 +
      N("C") ~ N("F") % 1.0

    println(network)
  }
}
