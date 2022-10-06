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


    println(s"Using Scala.js version ${System.getProperty("java.vm.version")}")
  }
}
