package mathlib.probability

case class Conditional1[A, B](value: A, condition: B) {
  override def toString: String = s"$value | $condition"
}

case class Conditional2[A, B](value: A, prior: Distribution[B]) {
  override def toString: String = s"$value | $prior"
}