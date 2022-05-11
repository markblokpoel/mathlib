package example

import mathlib.demos.VertexCover._
import mathlib.graph._
import mathlib.set.SetTheory.ImplSet


object Main {
  def main(args: Array[String]): Unit = {
//    println(Set(1, 2, 3) x Set("a", "b"))
//
//    val signals = Set("monkey", "pizza", "tree", "couch") // domain
//    val signalPriors = signals.uniformDistribution // create uniform distribution
//    signalPriors.hist() // print histogram
//    val p1 = pr("monkey", signalPriors) // request probability
//    println(p1)
//    println("H(signalPriors) = " + signalPriors.entropy)


    // Vertex cover demo
    import mathlib.graph.GraphImplicits._

    val graph = Graph.empty +
      N("A") ~ N("B") +
      N("A") ~ N("C") +
      N("B") ~ N("C") +
      N("C") ~ N("D") +
      N("C") ~ N("E") +
      N("C") ~ N("F")

    println("exhaustive search")
    vertexCover(graph, 3).foreach(println)

    println("fpt algorithm")
    fptVertexCover(graph, 3, Set.empty).foreach(println)

    println("templated bounded search tree")
    fptvc(graph, 3, Set.empty).foreach(println)

    println(s"Using a JVM version ${System.getProperty("java.vm.version")}")
  }
}
