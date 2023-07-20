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


//    graphs.foreach(println)
//    graphs.foreach {
//      case g: UnweightedGraph[_, _] => println(g.adjacencyList)
//      case g: WeightedGraph[_, _] => println(g.adjacencyList)
//      case _ => println("Unknown graph supertype.")
//    }
//    hyperGraphs.foreach(println)

    val test = DiGraph(Set(
      "a" ~> "b",
      "b" ~> "c",
//      "c" ~> "d",
//      "d" ~> "a"
    ))

    println(test)
    println(test.containsCycle)


    val test2 = UnDiGraph(Set(
      "a" ~ "b",
      "b" ~ "c",
//      "c" ~ "a"
    ))
    println(test2.containsCycle)

  }
}
