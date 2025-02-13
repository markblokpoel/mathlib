package mathlib.demos

import mathlib.graph.DiGraph
import mathlib.graph.GraphImplicits._
import mathlib.probability._
import mathlib.probability.datastructures._
object ProbabilityDemo {


  def main(args: Array[String]): Unit = {

    def v1Pr(a: BooleanDomain): BigDecimal = if(a == True) 1.0 else 0.0

    val v1 = new DiscreteVariable("v1", BooleanDomain, v1Pr)


    val bla = BooleanDomain.True

//    println(new BayesianNetwork(DiGraph(Set(v1 ~> v2))))
  }
}
