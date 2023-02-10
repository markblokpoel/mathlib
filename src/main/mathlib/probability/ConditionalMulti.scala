package mathlib.probability

import mathlib.probability.datastructures.DiscreteDomain

case class ConditionalMulti (value: DiscreteDomain, prior: ConditionalMulti) {
  override def toString: String = s"$value | $prior"
}
