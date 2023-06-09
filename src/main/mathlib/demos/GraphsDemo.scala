package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph._
import mathlib.graph.hyper._

object GraphsDemo {
  def main(args: Array[String]): Unit = {
    val graphs = Seq(
      DiGraph(Set("a", "b", "c"), Set("a" ~> "b", "c" ~> "b")),
      UnDiGraph(Set("a", "b", "c"), Set("a" ~ "b", "c" ~ "b")),
      WDiGraph(Set("a", "b", "c"), Set("a" ~> "b" % 0.5, "c" ~> "b" % 0.2)),
      WUnDiGraph(Set("a", "b", "c"), Set("a" ~ "b" % 0.4, "c" ~ "b" % 0))
    )
    val hyperGraphs = Seq(
      DiHyperGraph(
        Set("A", "B", "C"),
        Set(
          Set("A", "B") ~> Set("C")
        )
      ),
      UnDiHyperGraph(
        Set("A", "B", "C"),
        Set(
          Set("A", "B") ~ Set("C")
        )
      ),
      WDiHyperGraph(
        Set("A", "B", "C"),
        Set(
          Set("A", "B") ~> Set("C") % 0.5
        )
      ),
      WUnDiHyperGraph(
        Set("A", "B", "C"),
        Set(
          Set("A", "B") ~ Set("C") % 0.5
        )
      )
    )


    graphs.foreach(println)
    graphs.map(_.adjacencyList).foreach(println)
    hyperGraphs.foreach(println)

    // TODO Check nthAdjacencyList for undirected graphs. It now cycles back and forth.
    val test = UnDiGraph(Set(
      "a" ~ "b",
      "b" ~ "c",
      "c" ~ "d"
    ))

    println(test)

    println(test.nthAdjacencyList(0))
    println(test.nthAdjacencyList(1))
    println(test.nthAdjacencyList(2))
    println(test.nthAdjacencyList(3))
    println(test.nthAdjacencyList(4))


  }
}
