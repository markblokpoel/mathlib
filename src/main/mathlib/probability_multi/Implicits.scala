package mathlib.probability_multi

import mathlib.probability_multi.datastructures.BigNatural

object Implicits {
  implicit class ImplBigNaturalDouble(d: Double) {
    def bigNatural: BigNatural = BigNatural(d)
  }
}
