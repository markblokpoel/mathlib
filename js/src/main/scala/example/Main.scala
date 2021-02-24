package example

import mathlib.graph.{Node, WUnDiEdge}

//import mathlib.demos.Coherence._
import mathlib.graph.GraphImplicits._
object Main {

  def main(args: Array[String]): Unit = {

    val v1 = Node("A")
    val v2 = Node("B")

    val e12 = WUnDiEdge(v1, v2, 3)

    println(e12)

    val e34 = "D" ~ "E" % 3.0

    println(e34)
//
//    val beliefs: Set[Belief] = Set("A", "B", "C", "D")
//    val positiveConstraints: Set[WeUnEdge[Belief]] = Set(
//      (Set("A", "B"), 1.0),
//      (Set("A", "C"), 1.0),
//      (Set("A", "D"), 1.0),
//      (Set("B", "C"), 1.0)
//    )
//    val negativeConstraints: Set[WeUnEdge[Belief]] = Set(
//      (Set("B", "D"), 1.0),
//      (Set("C", "D"), 1.0)
//    )
//
//    val data = Set("A", "D")
//
//    def dataValueAssignment(belief: Belief): Boolean = belief match {
//      case "A" => true
//      case "D" => true
//      case _ => false
//    }
//
//    val tva = foundationalCoherence(
//      beliefs,
//      positiveConstraints,
//      negativeConstraints,
//      data,
//      dataValueAssignment)
//
//    if(tva._2.isDefined) {
//      println("Coherence score: " + tva._1)
//      val va = tva._2.get
//      for(belief <- beliefs)
//        println(belief + " -> " + va(belief))
//    }


//    println(Set(1, 2, 3) x Set("a", "b"))
//
//    val signals = Set("monkey", "pizza", "tree", "couch") // domain
//    val signalPriors = signals.uniformDistribution // create uniform distribution
//    signalPriors.hist() // print histogram
//    val p1 = pr("monkey", signalPriors) // request probability
//    println(p1)
//    println("H(signalPriors) = " + signalPriors.entropy)

    println(s"Using Scala.js version ${System.getProperty("java.vm.version")}")
  }
}
