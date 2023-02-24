package mathlib.demos

import mathlib.probability_multi.{DiscreteConditionalDistribution, DiscreteDistribution}
import mathlib.probability_multi.Implicits._


object Probability {

  def main(args: Array[String]): Unit = {

    val letters = DiscreteDistribution(
      id = "Letter",
      Set("a", "b", "c"),
      Map(
        "a" -> 0.2.bigNatural,
        "b" -> 0.5.bigNatural,
        "c" -> 0.3.bigNatural
      )
    )

    letters.hist()

    val prior = letters is "a" or "c"

    val names = DiscreteConditionalDistribution(
      id = "Name",
      domain = Set(
        "mark",
        "steffie"
      ),
      Map(

      ),
      letters:.*
    )

    val bla = (names is "mark") | (letters is "a" or "c")

    println(bla)

  }
}
