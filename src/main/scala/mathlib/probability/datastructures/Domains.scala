package mathlib.probability.datastructures

sealed trait Domain

case class DiscreteDomain(range: Set[String], pr: String => BigDecimal) extends Domain

case class ContinuousDomain(pr: BigDecimal => BigDecimal) extends Domain