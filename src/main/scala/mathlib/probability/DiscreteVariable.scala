package mathlib.probability

import mathlib.probability.datastructures.{DiscreteDomain, Domain}

class DiscreteVariable(label: String, discreteDomain: DiscreteDomain)
    extends Variable(label = label) {
  override def prior(a: String): BigDecimal = discreteDomain.pr(a)

}
