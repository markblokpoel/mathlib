package mathlib.demos

import mathlib.graph.GraphImplicits._
import mathlib.graph._
import mathlib.graph.hyper._

object GraphsDemo {
  def main(args: Array[String]): Unit = {
    val vertex1 = Node("a")                 // Use case class Node to construct a vertex
    val vertex2 = Node("b")
    val vertex3 = N("c")             // Shorthand
    val edge12 = UnDiEdge(vertex1, vertex2) // Use case class UnDiEdge to construct an undirected edge
    val edge23 = vertex2 ~ vertex3          // Use ~ to construct an undirected edge
    val edge34 = "c" ~ "d"                  // Ommitting node case class is possible, "c" and "d" will
                                            // be automatically wrapped in Node(.)
    val firstGraph = UnDiGraph(             // Use case class to construct an undirected graph
      vertices = Set(vertex1, vertex2, vertex3, Node("d")),
      edges = Set(edge12, edge23, edge34)
    )
    println(firstGraph)                     // Print the graph

    // More examples of graphs and hyper graphs below, using shorthands.
    val graphs = Seq(
      DiGraph(
        vertices = Set("a", "b", "c"),
        edges = Set("a" ~> "b", "c" ~> "b")
      ),
      UnDiGraph(
        vertices = Set("a", "b", "c"),
        edges = Set("a" ~ "b", "c" ~ "b")
      ),
      WDiGraph(
        vertices = Set("a", "b", "c"),
        edges = Set("a" ~> "b" % 0.5, "c" ~> "b" % 0.2)
      ),
      WUnDiGraph(
        vertices = Set("a", "b", "c"),
        edges = Set("a" ~ "b" % 0.4, "c" ~ "b" % 0)
      )
    )
    val hyperGraphs = Seq(
      DiHyperGraph(
        vertices = Set("A", "B", "C"),
        edges = Set(
          Set("A", "B") ~> Set("C")
        )
      ),
      UnDiHyperGraph(
        vertices = Set("A", "B", "C"),
        edges = Set(
          Set("A", "B") ~ Set("C")
        )
      ),
      WDiHyperGraph(
        vertices = Set("A", "B", "C"),
        edges = Set(
          Set("A", "B") ~> Set("C") % 0.5
        )
      ),
      WUnDiHyperGraph(
        vertices = Set("A", "B", "C"),
        edges = Set(
          Set("A", "B") ~ Set("C") % 0.5
        )
      )
    )
    // Print the examples.
    println("** Graph examples **")
    graphs.foreach(println)
    println("** Hyper graph examples **")
    hyperGraphs.foreach(println)
    // Example graph to check for cycles
    println("** Check for cycle example **")
    val cycleCheck = DiGraph(Set(
      "a" ~> "b",
      "b" ~> "c",
      "c" ~> "d",
      "d" ~> "a"
    ))
    println(cycleCheck)
    println("Contains a cycle: " + cycleCheck.containsCycle)
    // Print the adjacency graph for the checkCycle graph
    println("Adjacency lists")
    println("1st order: " + cycleCheck.adjacencyList)
    // Print the 2nd adjacency graph for the checkCycle graph
    println("2nd order: " + cycleCheck.nthAdjacencyList(2))
    println(cycleCheck.degreeOf(N("a")))

    println(UnDiGraph.barabasiAlbertGraph(100, 10).toDOTString)
  }
}
