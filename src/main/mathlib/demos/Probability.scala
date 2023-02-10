package mathlib.demos

import mathlib.probability_multi.DiscreteDistribution
import mathlib.probability_multi.Implicits._


object Probability {

  def main(args: Array[String]): Unit = {

    val dist = DiscreteDistribution(
      id = "Letters",
      Set("A", "B", "C"),
      Map(
        "A" -> 0.2.bigNatural,
        "B" -> 0.5.bigNatural,
        "C" -> 0.3.bigNatural
      )
    )

    dist.hist()

    dist.sample(10).foreach(println)
    println(dist.error)
    println(dist.isNormalized)

  }
}
