package mathlib.demos

import mathlib.probability_multi.{DiscreteConditionalDistribution, DiscreteDistribution}
import mathlib.probability_multi.Implicits._
import mathlib.probability_multi.datastructures.{BigNatural, DiscreteDistributionValueAssignment, DistributionValueAssignment}


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

    val names = DiscreteConditionalDistribution[String](
      id = "Name",
      domain = Set(
        "mark",
        "steffie"
      ),
      Map(
        ("mark", Seq(letters is "a")) -> BigNatural(0.7),
        ("mark", Seq(letters is "b")) -> BigNatural(0.2),
        ("mark", Seq(letters is "c")) -> BigNatural(0.1),
        ("steffie", Seq(letters is "a")) -> BigNatural(0.3),
        ("steffie", Seq(letters is "b")) -> BigNatural(0.3),
        ("steffie", Seq(letters is "c")) -> BigNatural(0.4)
      ),
      letters
    )

    val bla = (names is "mark") | (letters is "a" or "c")

    println(bla)

  }
}
