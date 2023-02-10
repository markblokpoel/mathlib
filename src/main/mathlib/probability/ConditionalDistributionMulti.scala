package mathlib.probability

import mathlib.probability.datastructures.{DiscreteDomain, DiscreteValue}

case class ConditionalDistributionMulti(
    baseDomain: Set[_],
    conditionalDomains: Seq[Set[_]],
    distribution: Map[(_,Seq[_]), Double]
) {

}

val bla = ConditionalDistributionMulti(
  Set(true, false),
  Seq(
    Set(true, false),
    Set(true, false)
  ),
  Map(
    (true,  Seq(true, true)) -> 0.0,
    (false, Seq(true, true)) -> 0.0,
    (true,  Seq(true, false)) -> 0.0,
    (false, Seq(true, false)) -> 0.0,
    (true,  Seq(false, true)) -> 0.0,
    (false, Seq(false, true)) -> 0.0,
    (true,  Seq(false, false)) -> 0.0,
    (false, Seq(false, false)) -> 0.0
  )
)