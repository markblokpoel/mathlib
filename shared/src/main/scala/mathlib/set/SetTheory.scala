package mathlib.set

import scala.annotation.tailrec
import scala.util.Random

/**
 * Implementation of basic set theory as implicits
 */
object SetTheory {
  trait NumberSetOps[T] {
    def sumElements(set: Set[T]): T
    def mulElements(set: Set[T]): T
  }

  implicit object IntNumberOps extends NumberSetOps[Int] {
    override def sumElements(set: Set[Int]): Int = set.sum
    override def mulElements(set: Set[Int]): Int = set.product
  }

  implicit object DoubleNumberOps extends NumberSetOps[Double] {
    override def sumElements(set: Set[Double]): Double = set.sum
    override def mulElements(set: Set[Double]): Double = set.product
  }

  implicit object FloatNumberOps extends NumberSetOps[Float] {
    override def sumElements(set: Set[Float]): Float = set.sum
    override def mulElements(set: Set[Float]): Float = set.product
  }

  def powerset[A](set: Set[A]): Set[Set[A]] = set.subsets.toSet
  def P[A](set: Set[A]): Set[Set[A]] = powerset(set)
  def powerset[A](set: Set[A], len: Int): Set[Set[A]] = set.subsets(len).toSet
  def P[A](set: Set[A], len: Int): Set[Set[A]] = powerset(set, len)
  def powersetUp[A](set: Set[A], upperbound: Int): Set[Set[A]] =
    (for(len <- 0 to upperbound) yield powerset(set, len)).toSet.flatten
  def powersetLow[A](set: Set[A], lowerbound: Int): Set[Set[A]] =
    (for(len <- lowerbound to set.size) yield powerset(set, len)).toSet.flatten


  def argMax[A, T](set: Set[A], f: A => T)(implicit ord: Ordering[T]): Set[A] = {
    val max = set.map(f).max  // find max value
    set.filter(f(_) == max)           // return all elems with max value
  }

  def sum[T](set: Set[T])(implicit nso: NumberSetOps[T]): T = nso.sumElements(set)
  def sum[A, T](set: Set[A], f: A => T)(implicit nso: NumberSetOps[T]): T = nso.sumElements(set.map(f))
  def sum[A, T](set: Set[(A, A)], f: (A, A) => T)(implicit nso: NumberSetOps[T]): T = nso.sumElements(set.map(pair => f(pair._1, pair._2)))

  def product[T](set: Set[T])(implicit nso: NumberSetOps[T]): T = nso.mulElements(set)
  def product[A, T](set: Set[A], f: A => T)(implicit nso: NumberSetOps[T]): T = nso.mulElements(set.map(f))
  def product[A, T](set: Set[(A, A)], f: (A, A) => T)(implicit nso: NumberSetOps[T]): T = nso.mulElements(set.map(pair => f(pair._1, pair._2)))

  def random[A](set: Set[A]): Option[A] = if (set.isEmpty) None
  else Some(set.toList(Random.nextInt(set.size)))

  implicit class ImplAny[A](elem: A) {
    def in(set: Set[A]): Boolean = set.contains(elem)
  }

  implicit class ImplSet[A](set: Set[A]) {
    // for set membership, use set.contains(element)

    def isSubsetOf(set2: Set[A]): Boolean = set != set2 && set.subsetOf(set2)
    def <(set2: Set[A]): Boolean = isSubsetOf(set2)

    def isSubsetEqTo(set2: Set[A]): Boolean = set.subsetOf(set2)
    def <=(set2: Set[A]): Boolean = isSubsetEqTo(set2)

    def isSupersetOf(set2: Set[A]): Boolean = set2 isSubsetOf set
    def >(set2: Set[A]): Boolean = isSupersetOf(set2)

    def isSupersetEqTo(set2: Set[A]): Boolean = set2 isSubsetEqTo set
    def >=(set2: Set[A]): Boolean = isSupersetEqTo(set2)

    // for intersection use set.intersect(set2)
    def /\(set2: Set[A]): Set[A] = set.intersect(set2)

    // for union use set.union(set2)
    def \/(set2: Set[A]): Set[A] = set.union(set2)

    def build(f: A => Boolean): Set[A] = set.filter(f(_))

    def |(f: A => Boolean): Set[A] = set build f

    def \(set2: Set[A]): Set[A] = set.diff(set2)

    def cardinalProduct[B](set2: Set[B]): Set[(A, B)] =
      for (x <- set; y <- set2) yield (x, y)
    def x[B](set2: Set[B]): Set[(A, B)] = cardinalProduct(set2)

    def pairs: Set[(A, A)] = for (x <- set; y <- set) yield (x, y)

    def uniquePairs: Set[(A, A)] = for (x <- set; y <- set if x != y) yield (x, y)
    
    def unorderedPairs: Set[Set[A]] = for (x <- set; y <- set) yield Set(x, y)

    def unorderedUniquePairs: Set[Set[A]] = for (x <- set; y <- set if x != y) yield Set(x, y)

    def powerset: Set[Set[A]] = SetTheory.powerset(set)
    def P: Set[Set[A]] = SetTheory.powerset(set)

    def allPartitions: Set[Set[Set[A]]] = {
      if (set.isEmpty) Set.empty
      else {
        val hd = set.head
        val solutions = set.tail.allPartitions
        val part1 = if (solutions.isEmpty) Set(Set(Set(hd)))
        else solutions.map(partitioning => {
          partitioning + Set(hd)
        })
        val part2 = if (solutions.isEmpty) Set(Set(Set(hd)))
        else solutions.flatMap(partitioning => partitioning.map(part => {
          val a = part + hd
          val b = partitioning - part
          b + a
        }))
        part1.union(part2)
      }
    }

    def argMax[T](f: A => T)(implicit ord: Ordering[T]): Set[A] = SetTheory.argMax(set, f)

    def allBijections[B](target: Set[B]): Set[Map[A, B]] = {
      val perm = target.toList.permutations.toSet
      val bijections = perm
        .map(set zip _)
        .map(_.toMap)
      bijections
    }

    def allMappings[B](coDomain: Set[B]): Set[Map[A, B]] = {
      @tailrec
      def allMappingsRec(domain: Set[A], coDomain: Set[B], acc: Set[Map[A,B]] = Set(Map[A,B]())): Set[Map[A, B]] = {
        if(domain.isEmpty) acc
        else if(coDomain.isEmpty) acc
        else {
          val newMappings: Set[(A, B)] = coDomain.map(domain.head -> _)
          val newAcc = acc.flatMap(oldMapping => newMappings.map(oldMapping + _))
          allMappingsRec(domain.tail, coDomain, newAcc)
        }
      }

      allMappingsRec(set, coDomain)
    }
    
    def random: Option[A] = SetTheory.random(set)
  }
  implicit class Impl2Set[A, B](sets: (Set[A], Set[B])) {
    // Example (set, set2) build((a: Int, b: Int) => a/2==0 && b%2==0)
    def build(f: (A, B) => Boolean): Set[(A, B)] =
      (sets._1 cardinalProduct sets._2) build Function.tupled(f)
    def |(f: (A, B) => Boolean): Set[(A, B)] = sets build f
  }

  implicit class ImplSetSet[A](setOfSets: Set[Set[A]]) {
    def union: Set[A] =
      if (setOfSets.nonEmpty) setOfSets.reduce(_ union _) else Set.empty

    def intersection: Set[A] =
      if (setOfSets.nonEmpty) setOfSets.reduce(_ intersect _) else Set.empty
  }


  def requirement(b: Boolean, msg: String): Unit =
    if (!b) {
      println(s"Requirement not met: $msg")
      assert(false)
    }
}
