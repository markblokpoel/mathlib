package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph._
import mathlib.graph.hyper._

object GraphsDemo {
  def main(args: Array[String]): Unit = {
    Seq(
      DiGraph(Set("a", "b"), Set("a" ~> "b", "c" ~> "b")),
      UnDiGraph(Set("a", "b"), Set("a" ~ "b", "c" ~ "b")),
      WDiGraph(Set("a", "b"), Set("a" ~> "b" % 0.5, "c" ~> "b" % 0.2)),
      WUnDiGraph(Set("a", "b"), Set("a" ~ "b" % 0.4, "c" ~ "b" % 0.1)),
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
    ).foreach(println)

    println(
      DiGraph(Set("a", "b"), Set("a" ~> "b", "c" ~> "b"))
    )

  }
}
