package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph.WDiGraph
import mathlib.graph.hyper.WDiHyperGraph

object GraphsDemo {
  def main(args: Array[String]): Unit = {

    WDiHyperGraph(
      Set(N("A"), N("B"), N("C")),
      Set(
        Set("A", "B") ~> Set("C") % 0.5
      )
    )
  }
}
