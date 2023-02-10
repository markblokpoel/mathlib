package mathlib.probability.datastructures

sealed trait DiscreteDomain[A] extends Set[DiscreteValue[A]] {

}

final case class DiscreteValue[A](value: A)

final case class BooleanDomain(domain: Set[Boolean]) extends DiscreteDomain[Boolean] {

  private lazy val dvDomain = domain.iterator.map(b => DiscreteValue(b))
  override def iterator: Iterator[DiscreteValue[Boolean]] = dvDomain

  override def incl(elem: DiscreteValue[Boolean]): Set[DiscreteValue[Boolean]] = dvDomain + elem

  override def excl(elem: DiscreteValue[Boolean]): Set[DiscreteValue[Boolean]] = dvDomain - elem

  override def contains(elem: DiscreteValue[Boolean]): Boolean = dvDomain.contains(elem)
}
final case class StringDomain(domain: Set[String]) extends DiscreteDomain[String] {


  private lazy val dvDomain = domain.iterator.map(b => DiscreteValue(b))
  override def iterator: Iterator[DiscreteValue[String]] = dvDomain

  override def incl(elem: DiscreteValue[String]): Set[DiscreteValue[String]] = dvDomain + elem

  override def excl(elem: DiscreteValue[String]): Set[DiscreteValue[String]] = dvDomain - elem

  override def contains(elem: DiscreteValue[String]): Boolean = dvDomain.contains(elem)
}

final case class IntDomain(domain: Set[Int]) extends DiscreteDomain[Int] {

  private lazy val dvDomain = domain.iterator.map(b => DiscreteValue(b))

  override def iterator: Iterator[DiscreteValue[Int]] = dvDomain

  override def incl(elem: DiscreteValue[Int]): Set[DiscreteValue[Int]] = dvDomain + elem

  override def excl(elem: DiscreteValue[Int]): Set[DiscreteValue[Int]] = dvDomain - elem

  override def contains(elem: DiscreteValue[Int]): Boolean = dvDomain.contains(elem)
}

final case class DoubleDomain(domain: Set[Double]) extends DiscreteDomain[Double] {

  private lazy val dvDomain = domain.iterator.map(b => DiscreteValue(b))

  override def iterator: Iterator[DiscreteValue[Double]] = dvDomain

  override def incl(elem: DiscreteValue[Double]): Set[DiscreteValue[Double]] = dvDomain + elem

  override def excl(elem: DiscreteValue[Double]): Set[DiscreteValue[Double]] = dvDomain - elem

  override def contains(elem: DiscreteValue[Double]): Boolean = dvDomain.contains(elem)
}