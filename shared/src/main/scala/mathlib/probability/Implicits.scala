package mathlib.probability

/** Implicit functions for more concise syntax. */
object Implicits {

  implicit class ImplDistributionConstruct[A](domain: Set[A]) {
    /**
     * Constructs a uniform distribution over the domain.
     * @return
     */
    def uniformDistribution: Distribution[A] = {
      val distribution = domain.map(value => value -> 1.0 / domain.size.doubleValue()).toMap
      Distribution(domain, distribution)
    }

    /**
     * Constructs a distribution over the domain where pr(a) = 1.0 if v == value and 0.0 otherwise.
     * @param value value in the domain to construct distribution for
     * @return
     */
    @throws[IllegalArgumentException]
    def singleValueDistribution(value: A): Distribution[A] = {
      require(domain.contains(value), "Cannot construct distribution for value outside the domain.")
      Distribution(domain, domain.map(v => v -> (if(v == value) 1.0 else 0.0)).toMap)
    }
  }

  implicit class ImplConditional[A](value: A) {
    /**
     * Syntax for writing a conditional given a value.
     * @param condition condition value
     * @tparam B type of the condition domain
     * @return
     */
    def |[B](condition: B): Conditional1[A, B] = Conditional1(value, condition)

    /**
     * Syntax for writing a conditional given a distribution.
     * @param condition condition value
     * @tparam B type of the condition domain
     * @return
     */
    def |[B](condition: Distribution[B]): Conditional2[A, B] = Conditional2(value, condition)
  }

  implicit class ImplDouble(double: Double) {
    def *[A, B](cd: ConditionalDistribution[A, B]): ConditionalDistribution[A, B] = cd * double

    def *[A](cd: Distribution[A]): Distribution[A] = cd * double

    def log: Double = math.log(double)
    def exp: Double = math.exp(double)
  }

  implicit class ImplInt(int: Int) {
    /** Converts Int to Double. */
    def toDouble: Double = int.doubleValue()
  }

}
