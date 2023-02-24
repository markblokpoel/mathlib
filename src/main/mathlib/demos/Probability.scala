package mathlib.demos

import mathlib.probability_multi.{DiscreteConditionalDistribution, DiscreteDistribution}
import mathlib.probability_multi.Implicits._
import mathlib.probability_multi.datastructures.BigNatural


object Probability {

  def main(args: Array[String]): Unit = {

    val letters = DiscreteDistribution(
      id = "Letter",
      Set("a", "b", "c"),
      Map(
        "a" -> BigNatural(0.2),
        "b" -> BigNatural(0.5),
        "c" -> BigNatural(0.3)
      )
    )

    letters.hist()

    val prior = letters is "a" or "c"

    println(prior)

//    val names = DiscreteConditionalDistribution(
//      id = "Name",
//      domain = Set(
//        "mark",
//        "steffie"
//      ),
//      Map(
//
//      ),
//      letters:.*
//    )
//
//    val bla = (names is "mark") | (letters is "a" or "c")
//
//    println(bla)

  }
}
