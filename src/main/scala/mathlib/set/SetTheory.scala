package mathlib.set

import scala.annotation.tailrec
import scala.util.Random

/** Implementation of basic set theory. Some functions will have shorthand notation.
  */
object SetTheory {
  trait NumberSetOps[T] {
    def sumElements(set: Set[T]): T
    def mulElements(set: Set[T]): T

    def sumElements(list: List[T]): T

    def mulElements(list: List[T]): T
  }

  implicit object IntNumberOps extends NumberSetOps[Int] {
    override def sumElements(set: Set[Int]): Int   = set.sum
    override def mulElements(set: Set[Int]): Int   = set.product
    override def sumElements(list: List[Int]): Int = list.sum
    override def mulElements(list: List[Int]): Int = list.product
  }

  implicit object DoubleNumberOps extends NumberSetOps[Double] {
    override def sumElements(set: Set[Double]): Double = set.sum
    override def mulElements(set: Set[Double]): Double = set.product

    override def sumElements(list: List[Double]): Double = list.sum

    override def mulElements(list: List[Double]): Double = list.product
  }

  implicit object FloatNumberOps extends NumberSetOps[Float] {
    override def sumElements(set: Set[Float]): Float = set.sum
    override def mulElements(set: Set[Float]): Float = set.product

    override def sumElements(list: List[Float]): Float = list.sum

    override def mulElements(list: List[Float]): Float = list.product
  }

  /** Universal quantifier, returns true if and only if all elements in `set` return true for
    * function `f`.
    *
    * @param set
    *   The set of elements to compute universal quantifier for.
    * @param f
    *   The function to apply.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   True if all elements are true for `f`, false otherwise.
    */
  def forall[A](set: Set[A], f: A => Boolean): Boolean = set.forall(f)

  /** Existential quantifier, returns true if and only if one or more elements in `set` return true
    * for function `f`.
    *
    * @param set
    *   The set of elements to compute existential quantifier for.
    * @param f
    *   The function to apply.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   True if one or more elements are true for `f`, false otherwise.
    */
  def exists[A](set: Set[A], f: A => Boolean): Boolean = set.exists(f)

  /** Returns the power set.
    *
    * This function runs very slowly (in exponential time).
    * @param set
    *   The set of elements to compute power set for.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   The power set, which is a set of all possible subsets.
    */
  def powerset[A](set: Set[A]): Set[Set[A]] = set.subsets.toSet

  /** Returns the power set (shorthand notation).
    *
    * This function runs very slowly (in exponential time).
    * @param set
    *   The set of elements to compute power set for.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   The power set, which is a set of all possible subsets.
    */
  def P[A](set: Set[A]): Set[Set[A]] = powerset(set)

  /** Returns all possible subsets with `len ` number of elements.
    *
    * @param set
    *   The set of elements to compute power set for.
    * @param len
    *   The size of the subsets.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   The set with all possible subsets with `len ` number of elements.
    */
  def powerset[A](set: Set[A], len: Int): Set[Set[A]] = set.subsets(len).toSet

  /** Returns all possible subsets with `len ` number of elements (shorthand notation).
    * @param set
    *   The set of elements to compute power set for.
    * @param len
    *   The size of the subsets.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   The set with all possible subsets with `len ` number of elements.
    */
  def P[A](set: Set[A], len: Int): Set[Set[A]] = powerset(set, len)

  /** Returns all possible subsets with up to `upperbound ` number of elements.
    * @param set
    *   The set of elements to compute power set for.
    * @param upperbound
    *   The upperbound size of the subsets, i.e., the maximum number of elements any subset
    *   contains.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   The set of all possible subsets of up to `upperbound ` number of elements.
    */
  def powersetUp[A](set: Set[A], upperbound: Int): Set[Set[A]] =
    (for (len <- 0 to upperbound) yield powerset(set, len)).toSet.flatten

  /** Returns all possible subsets with at least `lowerbound ` number of elements.
    * @param set
    *   The set of elements to compute power set for.
    * @param lowerbound
    *   The lower bound size of the subsets, i.e., the minimum number of elements any subset
    *   contains.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   The set of all possible subsets with at least `lowerbound ` number of elements.
    */
  def powersetLow[A](set: Set[A], lowerbound: Int): Set[Set[A]] =
    (for (len <- lowerbound to set.size) yield powerset(set, len)).toSet.flatten

  /** Returns all elements from the set that have maximum value according to a given function `f `.
    *
    * The function `f ` needs to have as an argument the same type as the elements in the set (A),
    * and returns a value for which an ordering exists (e.g., a number or String).
    * @param set
    *   The set to select maximum elements from.
    * @param f
    *   The function for which the elements are evaluated.
    * @param ord
    *   An ordering on the return values of `f `, this can be omitted when using simple types such
    *   as numbers.
    * @tparam A
    *   The type of the elements in the set.
    * @tparam T
    *   The type of the values that are returned by `f `.
    * @return
    *   A set of elements that have maximum value according to `f `. Can contain zero, one or more
    *   elements.
    */
  def argMax[A, T](set: Set[A], f: A => T)(implicit ord: Ordering[T]): Set[A] = {
    if (set.isEmpty) set
    else {
      val max = set.map(f).max // find max value
      set.filter(f(_) == max) // return all elems with max value
    }
  }

  /** Returns the sum of all elements in the set, given that the elements can be added together.
    *
    * For sets with elements of types other than [[scala.Int]], [[scala.Double]] and
    * [[scala.Float]], one would need to implements a custom [[NumberSetOps]] implicit object. See
    * the source code of [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of elements.
    * @param nso
    *   A numeric operator that allows addition of the elements of the set, this can be omitted for
    *   [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam T
    *   The type of the elements in the set.
    * @return
    *   The sum of the elements in the set.
    */
  def sum[T](set: Set[T])(implicit nso: NumberSetOps[T]): T = nso.sumElements(set)

  /** Returns the sum of all elements in the set, given a function `f `.
    *
    * For sets with elements of types other than [[scala.Int]], [[scala.Double]] and
    * [[scala.Float]], one would need to implements a custom [[NumberSetOps]] implicit object. See
    * the source code of [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of elements.
    * @param f
    *   A function from elements in the set to elements of type `T ` that can be added together.
    * @param nso
    *   A numeric operator that allows addition of the elements of the set, this can be omitted for
    *   [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the set.
    * @tparam T
    *   The type of the values returned by `f `.
    * @return
    *   The sum of the elements in the set mapped via `f `.
    */
  def sum[A, T](set: Set[A], f: A => T)(implicit nso: NumberSetOps[T]): T =
    nso.sumElements(set.toList.map(f))

  /** Given a set of pairs (i.e., [[scala.Tuple2]]), this returns the sum of all pairs in the set,
    * given a function `f `.
    *
    * For sets with tuples of types other than [[scala.Int]], [[scala.Double]] and [[scala.Float]],
    * one would need to implements a custom [[NumberSetOps]] implicit object. See the source code of
    * [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of pairs of elements.
    * @param f
    *   A function from pairs in the set to elements of type `T ` that can be added together.
    * @param nso
    *   A numeric operator that allows addition of the elements of the set, this can be omitted for
    *   [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the pairs of the set.
    * @tparam T
    *   The type of the values returned by `f `.
    * @return
    *   The sum of the pairs of elements in the set mapped via `f `.
    */

  def sum[A, T](set: Set[(A, A)], f: (A, A) => T)(implicit nso: NumberSetOps[T]): T =
    nso.sumElements(set.toList.map(pair => f(pair._1, pair._2)))

  /** Returns the sum of all elements in the set for which ```b``` returns ```true```, given a
    * function ```f```.
    *
    * For sets with elements of types other than [[scala.Int]], [[scala.Double]] and
    * [[scala.Float]], one would need to implements a custom [[NumberSetOps]] implicit object. See
    * the source code of [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of elements.
    * @param b
    *   A function from elements in the set to [[scala.Boolean]], only pairs with true value are
    *   summed.
    * @param f
    *   A function from elements in the set to elements of type ```T``` that can be added together.
    * @param nso
    *   A numeric operator that allows addition of the elements of the set, this can be omitted for
    *   [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the set.
    * @tparam T
    *   The type of the values returned by ```f```.
    * @return
    *   The sum of the elements in the set mapped via ```f```.
    */
  def sum[A, T](set: Set[A], b: A => Boolean, f: A => T)(implicit nso: NumberSetOps[T]): T =
    nso.sumElements(set.build(b).toList.map(f))

  /** Given a set of pairs (i.e., [[scala.Tuple2]]), this returns the sum of all pairs in the set
    * for which ```b``` returns ```true```, given a function ```f```.
    *
    * For sets with tuples of types other than [[scala.Int]], [[scala.Double]] and [[scala.Float]],
    * one would need to implements a custom [[NumberSetOps]] implicit object. See the source code of
    * [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of pairs of elements.
    * @param b
    *   A function from pairs in the set to [[scala.Boolean]], only pairs with true value are
    *   summed.
    * @param f
    *   A function from pairs in the set to elements of type ```T``` that can be added together.
    * @param nso
    *   A numeric operator that allows addition of the elements of the set, this can be omitted for
    *   [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the pairs of the set.
    * @tparam T
    *   The type of the values returned by ```f```.
    * @return
    *   The sum of the pairs of elements in the set mapped via ```f```.
    */
  def sum[A, T](set: Set[(A, A)], b: ((A, A)) => Boolean, f: (A, A) => T)(implicit
      nso: NumberSetOps[T]
  ): T =
    nso.sumElements(set.build(b).toList.map(pair => f(pair._1, pair._2)))

  /** Returns the product of all elements in the set, given that the elements can be multiplied.
    *
    * For sets with elements of types other than [[scala.Int]], [[scala.Double]] and
    * [[scala.Float]], one would need to implements a custom [[NumberSetOps]] implicit object. See
    * the source code of [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of elements.
    * @param nso
    *   A numeric operator that allows multiplication of the elements of the set, this can be
    *   omitted for [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam T
    *   The type of the elements in the set.
    * @return
    *   The product of the elements in the set.
    */
  def product[T](set: Set[T])(implicit nso: NumberSetOps[T]): T = nso.mulElements(set)

  /** Returns the product of all elements in the set, given a function `f `.
    *
    * For sets with elements of types other than [[scala.Int]], [[scala.Double]] and
    * [[scala.Float]], one would need to implements a custom [[NumberSetOps]] implicit object. See
    * the source code of [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of elements.
    * @param f
    *   A function from elements in the set to elements of type `T ` that can be multiplied.
    * @param nso
    *   A numeric operator that allows multiplication of the elements of the set, this can be
    *   omitted for [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the set.
    * @tparam T
    *   The type of the values returned by `f `.
    * @return
    *   The product of the elements in the set mapped via `f `.
    */
  def product[A, T](set: Set[A], f: A => T)(implicit nso: NumberSetOps[T]): T =
    nso.mulElements(set.toList.map(f))

  /** Given a set of pairs (i.e., [[scala.Tuple2]]), this returns the product of all pairs in the
    * set, given a function `f `.
    *
    * For sets with tuples of types other than [[scala.Int]], [[scala.Double]] and [[scala.Float]],
    * one would need to implements a custom [[NumberSetOps]] implicit object. See the source code of
    * [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of pairs of elements.
    * @param f
    *   A function from pairs in the set to elements of type `T ` that can be multiplied together.
    * @param nso
    *   A numeric operator that allows multiplication of the elements of the set, this can be
    *   omitted for [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the pairs of the set.
    * @tparam T
    *   The type of the values returned by `f `.
    * @return
    *   The product of the pairs of elements in the set mapped via `f `.
    */
  def product[A, T](set: Set[(A, A)], f: (A, A) => T)(implicit nso: NumberSetOps[T]): T =
    nso.mulElements(set.toList.map(pair => f(pair._1, pair._2)))

  /** Returns the product of all elements in the set for which ```b``` returns ```true```, given a
    * function ```f```.
    *
    * For sets with elements of types other than [[scala.Int]], [[scala.Double]] and
    * [[scala.Float]], one would need to implements a custom [[NumberSetOps]] implicit object. See
    * the source code of [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of elements.
    * @param b
    *   A function from elements in the set to [[scala.Boolean]], only pairs with true value are
    *   summed.
    * @param f
    *   A function from elements in the set to elements of type ```T``` that can be multiplied.
    * @param nso
    *   A numeric operator that allows multiplication of the elements of the set, this can be
    *   omitted for [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the set.
    * @tparam T
    *   The type of the values returned by ```f```.
    * @return
    *   The product of the elements in the set mapped via ```f```.
    */
  def product[A, T](set: Set[A], b: A => Boolean, f: A => T)(implicit nso: NumberSetOps[T]): T =
    nso.mulElements(set.build(b).toList.map(f))

  /** Given a set of pairs (i.e., [[scala.Tuple2]]), this returns the product of all pairs in the
    * set for which ```b``` returns ```true```, given a function ```f```.
    *
    * For sets with tuples of types other than [[scala.Int]], [[scala.Double]] and [[scala.Float]],
    * one would need to implements a custom [[NumberSetOps]] implicit object. See the source code of
    * [[IntNumberOps]], [[DoubleNumberOps]] and [[FloatNumberOps]] for examples.
    * @param set
    *   The set of pairs of elements.
    * @param b
    *   A function from pairs in the set to [[scala.Boolean]], only pairs with true value are
    *   summed.
    * @param f
    *   A function from pairs in the set to elements of type ```T``` that can be multiplied
    *   together.
    * @param nso
    *   A numeric operator that allows multiplication of the elements of the set, this can be
    *   omitted for [[scala.Int]], [[scala.Double]] and [[scala.Float]].
    * @tparam A
    *   The type of the elements in the pairs of the set.
    * @tparam T
    *   The type of the values returned by ```f```.
    * @return
    *   The product of the pairs of elements in the set mapped via ```f```.
    */
  def product[A, T](set: Set[(A, A)], b: ((A, A)) => Boolean, f: (A, A) => T)(implicit
      nso: NumberSetOps[T]
  ): T =
    nso.mulElements(set.build(b).toList.map(pair => f(pair._1, pair._2)))

  /** Selects a random elements from the set.
    * @param set
    *   The set of elements.
    * @tparam A
    *   The type of the elements in the set.
    * @return
    *   A random elements from the set, wrapped in `Some(.) `, `None ` if the set is emtpy.
    */
  def random[A](set: Set[A]): Option[A] = if (set.isEmpty) None
  else Some(set.toList(Random.nextInt(set.size)))

  /** Implicit functions for elements of any type.
    * @param elem
    *   The element.
    * @tparam A
    *   The type of the element.
    */
  implicit class ImplAny[A](elem: A) {

    /** Checks if the element is in the set.
      * @param set
      *   The set of elements.
      * @return
      *   `true ` if the `elem ` is in the `set `, `false ` otherwise.
      */
    def in(set: Set[A]): Boolean = set.contains(elem)
  }

  @tailrec
  private def perms[A](n: Int, set: Set[A], permutations: Set[List[A]]): Set[List[A]] =
    if (n == 0) permutations
    else {
      val nextPermutations: Set[List[A]] =
        for (permutation <- permutations; x <- set)
          yield x :: permutation
      perms(n - 1, set, nextPermutations)
    }

  /** Implicit functions for a set.
    * @param set
    *   The set of elements.
    * @tparam A
    *   The type of the elements in the set.
    */
  implicit class ImplSet[A](set: Set[A]) {
    // for contains use set.contains(otherSet)

    /** Checks if `set ` is a strict subset of `otherSet `.
      * @param otherSet
      *   The other set of elements with the same type as the elements in `set `.
      * @return
      *   `true ` if `set ` is a strict subset of `otherSet `, `false ` otherwise.
      */
    def isSubsetOf(otherSet: Set[A]): Boolean = set != otherSet && set.subsetOf(otherSet)

    /** Checks if `set ` is a strict subset of `otherSet ` (shorthand notation).
      *
      * @param otherSet
      *   The other set of elements with the same type as the elements in `set `.
      * @return
      *   `true ` if `set ` is a strict subset of `otherSet `, `false ` otherwise.
      */
    def <(otherSet: Set[A]): Boolean = isSubsetOf(otherSet)

    /** Checks if `set ` is a subset of or equal to `otherSet `.
      * @param otherSet
      *   The other set of elements with the same type as the elements in `set `.
      * @return
      *   `true ` if `set ` is a subset of or equal to `otherSet `, `false ` otherwise.
      */
    def isSubsetEqTo(otherSet: Set[A]): Boolean = set.subsetOf(otherSet)

    /** Checks if `set ` is a subset of or equal to `otherSet ` (shorthand notation).
      * @param otherSet
      *   The other set of elements with the same type as the elements in `set `.
      * @return
      *   `true ` if `set ` is a subset of or equal to `otherSet `, `false ` otherwise.
      */
    def <=(otherSet: Set[A]): Boolean = isSubsetEqTo(otherSet)

    def isSupersetOf(otherSet: Set[A]): Boolean = otherSet isSubsetOf set
    def >(otherSet: Set[A]): Boolean            = isSupersetOf(otherSet)

    /** Checks if `set ` is a superset of or equal to `otherSet `.
      * @param otherSet
      *   The other set of elements with the same type as the elements in `set `.
      * @return
      *   `true ` if `set ` is a superset of or equal to `otherSet `, `false ` otherwise.
      */
    def isSupersetEqTo(otherSet: Set[A]): Boolean = otherSet isSubsetEqTo set

    /** Checks if `set ` is a superset of `otherSet ` (shorthand notation).
      * @param otherSet
      *   The other set of elements with the same type as the elements in `set `.
      * @return
      *   `true ` if `set ` is a superset of or equal to `otherSet `, `false ` otherwise.
      */
    def >=(otherSet: Set[A]): Boolean = isSupersetEqTo(otherSet)

    // for intersection use set.intersect(set2)

    /** Returns the intersection between `set ` and `otherSet `.
      * @param otherSet
      *   A set of elements.
      * @return
      *   The intersection between `set ` and `otherSet `.
      */
    def /\(otherSet: Set[A]): Set[A] = set.intersect(otherSet)

    // for union use set.union(set2)

    /** Returns the union between `set ` and `otherSet `.
      * @param otherSet
      *   A set of elements.
      * @return
      *   The union between `set ` and `otherSet `.
      */
    def \/(otherSet: Set[A]): Set[A] = set.union(otherSet)

    /** Set builder.
      *
      * Builds a set from the base set by keeping only elements that return `true ` when `f ` is
      * applied to those elements.
      * @param f
      *   A function from the elements in the set to `true ` or `false `.
      * @return
      *   A subset of elements that satisfy `f `.
      */
    def build(f: A => Boolean): Set[A] = set.filter(f(_))

    /** Set builder (shorthand notation).
      *
      * Builds a set from the base set by keeping only elements that return `true ` when `f ` is
      * applied to those elements.
      * @param f
      *   A function from the elements in the set to `true ` or `false `.
      * @return
      *   A subset of elements that satisfy `f `.
      */
    def |(f: A => Boolean): Set[A] = set build f

    /** Returns the set difference `set \ otherSet `.
      *
      * For example, `Set(1, 2, 3) \ Set(3, 4) ` returns `Set(1, 2) `.
      * @param otherSet
      *   The other set of elements.
      * @return
      *   The set difference `set \ otherSet `.
      */
    def \(otherSet: Set[A]): Set[A] = set.diff(otherSet)

    /** Returns the cartesian product `set x otherSet `.
      *
      * For example, `Set(1, 2, 3).cartesianProduct(Set("a", "b")) ` returns `Set((2,"b"), (1,"b"),
      * (3,"a"), (3,"b"), (2,"a"), (1,"a")) `.
      * @param otherSet
      *   The other set of elements.
      * @tparam B
      *   The type of the elements of `otherSet `.
      * @return
      *   The cartesian product `set x otherSet `.
      */
    def cartesianProduct[B](otherSet: Set[B]): Set[(A, B)] =
      for (x <- set; y <- otherSet) yield (x, y)

    /** Returns the cartesian product `set x otherSet ` (shorthand notation).
      *
      * For example, `Set(1, 2, 3).cartesianProduct(Set("a", "b")) ` returns `Set((2,"b"), (1,"b"),
      * (3,"a"), (3,"b"), (2,"a"), (1,"a")) `.
      * @param otherSet
      *   The other set of elements.
      * @tparam B
      *   The type of the elements of `otherSet `.
      * @return
      *   The cartesian product `set x otherSet `.
      */
    def x[B](otherSet: Set[B]): Set[(A, B)] = cartesianProduct(otherSet)

    /** Returns a set of all possible pairs.
      * @return
      *   A set of all possible pairs.
      */
    def pairs: Set[(A, A)] = permutations2

    /** Returns a set of all possible unique pairs.
      *
      * This set excludes 'self' pairs (x, x).
      * @return
      *   A set of all possible unique pairs.
      */
    def uniquePairs: Set[(A, A)] = for (x <- set; y <- set if x != y) yield (x, y)

    /** Returns a set of all possible pairs, assuming that (a, b) equals (b, a).
      *
      * The pairs are represented by tuples.
      * @return
      *   A set of all possible pairs, assuming no ordering on the elements in pairs.
      */
    def unorderedPairs: Set[(A, A)] =
      (for (x <- set; y <- set) yield Set(x, y))
        .map(p => {
          val s = p.toSeq
          if(s.size == 1) (s(0), s(0))
          else (s(0), s(1))
        })

    /** Returns a set of all possible unique pairs, assuming that (a, b) equals (b, a).
      *
      * This set excludes 'self' pairs (x, x) and the pairs are represented by sets of size two.
      * @return
      *   A set of all possible pairs, assuming no ordering on the elements in pairs.
      */
    def unorderedUniquePairs: Set[(A, A)] = combinations2

    // TODO Write scaladoc
    def combinations(n: Int): Set[Set[A]] = set.toList.combinations(n).map(_.toSet).toSet
    // TODO Write scaladoc
    def combinations2: Set[(A, A)] = set
      .combinations(2)
      .map(p => {
        val s = p.toSeq
        (s(0), s(1))
      })
    // TODO Write scaladoc
    def combinations3: Set[(A, A, A)] = set
      .combinations(3)
      .map(p => {
        val s = p.toSeq
        (s(0), s(1), s(2))
      })
    // TODO Write scaladoc
    def combinations4: Set[(A, A, A, A)] = set
      .combinations(4)
      .map(p => {
        val s = p.toSeq
        (s(0), s(1), s(2), s(3))
      })
    // TODO Write scaladoc
    def permutations(n: Int): Set[List[A]] = perms(n, set, Set(List.empty))
    // TODO Write scaladoc
    def permutations2: Set[(A, A)] = perms(2, set, Set(List.empty[A])).map(p => {
      val s = p.toSeq
      (s(0), s(1))
    })
    // TODO Write scaladoc
    def permutations3: Set[(A, A, A)] = perms(3, set, Set(List.empty[A])).map(p => {
      val s = p.toSeq
      (s(0), s(1), s(2))
    })
    // TODO Write scaladoc
    def permutations4: Set[(A, A, A, A)] = perms(4, set, Set(List.empty[A])).map(p => {
      val s = p.toSeq
      (s(0), s(1), s(2), s(3))
    })

    /** Returns the power set of set.
      * @return
      *   The power set of set.
      */
    def powerset: Set[Set[A]] = SetTheory.powerset(set)

    /** Returns the power set of set (shorthand notation).
      * @return
      *   The power set of set.
      */
    def P: Set[Set[A]] = SetTheory.powerset(set)

    /** Returns all possible partitions of the set.
      *
      * For example, `Set(1, 2, 3).allPartitions ` returns {{{Set(Set(Set(3), Set(2), Set(1)),
      * Set(Set(3), Set(2, 1)), Set(Set(2), Set(3, 1)), Set(Set(3, 2), Set(1)), Set(Set(3, 2,
      * 1)))}}}.
      * @return
      *   All possible partitions of the set.
      */
    def allPartitions: Set[Set[Set[A]]] = {
      if (set.isEmpty) Set.empty
      else {
        val hd        = set.head
        val solutions = set.tail.allPartitions
        val part1 =
          if (solutions.isEmpty) Set(Set(Set(hd)))
          else
            solutions.map(partitioning => {
              partitioning + Set(hd)
            })
        val part2 =
          if (solutions.isEmpty) Set(Set(Set(hd)))
          else
            solutions.flatMap(partitioning =>
              partitioning.map(part => {
                val a = part + hd
                val b = partitioning - part
                b + a
              })
            )
        part1.union(part2)
      }
    }

    /** Returns all elements from the set that have maximum value according to a given function `f
      * `.
      *
      * The function `f ` needs to have as an argument the same type as the elements in the set (A),
      * and returns a value for which an ordering exists (e.g., a number or String).
      * @param f
      *   The function for which the elements are evaluated.
      * @param ord
      *   An ordering on the return values of `f `, this can be omitted when using simple types such
      *   as numbers.
      * @tparam T
      *   The type of the values that are returned by `f `.
      * @return
      *   A set of elements that have maximum value according to `f `. Can contain zero, one or more
      *   elements.
      */
    def argMax[T](f: A => T)(implicit ord: Ordering[T]): Set[A] = SetTheory.argMax(set, f)

    /** Returns a map with all possible 1-to-1 mappings (bijections) from elements in `set ` to
      * `targetSet `.
      *
      * Assumes that `set ` and `targetSet ` are of equal size. Will otherwise ignore elements of
      * the bigger set.
      * @param targetSet
      *   The target set of elements.
      * @tparam B
      *   The type of the elements in `targetSet `.
      * @return
      *   A map with all possible mappings from elements in `set ` to `targetSet `.
      */
    def allBijections[B](targetSet: Set[B]): Set[Map[A, B]] = {
      val perm = targetSet.toList.permutations.toSet
      val bijections = perm
        .map(set zip _)
        .map(_.toMap)
      bijections
    }

    /** Returns all possible one-to-many mappings from elementsin `set ` to `coDomain `.
      *
      * For example, `Set(1, 2, 3).allMappings(Set("a", "b")) ` returns {{{HashSet(Map(1 -> b, 2
      * -> b, 3 -> b), Map(1 -> a, 2 -> b, 3 -> a), Map(1 -> b, 2 -> a, 3 -> b), Map(1 -> b, 2 -> b,
      * 3 -> a), Map(1 -> a, 2 -> a, 3 -> a), Map(1 -> a, 2 -> b, 3 -> b), Map(1 -> b, 2 -> a, 3 ->
      * a), Map(1 -> a, 2 -> a, 3 -> b))}}}.
      * @param coDomain
      *   The set of elements representing the co-domain.
      * @tparam B
      *   The type of the elements in the co-domain.
      * @return
      *   All possible one-to-many mappings from elementsin `set ` to `coDomain `.
      */
    def allMappings[B](coDomain: Set[B]): Set[Map[A, B]] = {
      @tailrec
      def allMappingsRec(
          domain: Set[A],
          coDomain: Set[B],
          acc: Set[Map[A, B]] = Set(Map[A, B]())
      ): Set[Map[A, B]] = {
        if (domain.isEmpty) acc
        else if (coDomain.isEmpty) acc
        else {
          val newMappings: Set[(A, B)] = coDomain.map(domain.head -> _)
          val newAcc                   = acc.flatMap(oldMapping => newMappings.map(oldMapping + _))
          allMappingsRec(domain.tail, coDomain, newAcc)
        }
      }

      allMappingsRec(set, coDomain)
    }

    /** Selects a random elements from the set.
      * @return
      *   A random elements from the set, wrapped in `Some(.) `, `None ` if the set is emtpy.
      */
    def random: Option[A] = SetTheory.random(set)
  }
  // TODO Write scaladoc
  implicit class ImplTuple2Set[A, B](setTuple2: Set[(A, B)]) {
    def x[C](set: Set[C]): Set[(A, B, C)] =
      for (pair <- setTuple2; x <- set) yield (pair._1, pair._2, x)
  }
  // TODO Write scaladoc
  implicit class ImplTuple3Set[A, B, C](setTuple3: Set[(A, B, C)]) {
    def x[D](set: Set[D]): Set[(A, B, C, D)] =
      for (triplet <- setTuple3; x <- set) yield (triplet._1, triplet._2, triplet._3, x)
  }

  /** Implicit functions for pairs of sets.
    * @param sets
    *   A pair of sets.
    * @tparam A
    *   Type of the elements in the one set in the pair.
    * @tparam B
    *   Type of the elements in the other set in the pair.
    */
  implicit class Impl2Set[A, B](sets: (Set[A], Set[B])) {

    /** Builds a set from a pair of sets.
      *
      * It takes the cartesian product of the two sets, then keeps only pairs of elements that
      * return `true ` when `f ` is applied to those elements.
      *
      * Example to build a set of pairs from two initial sets, where one element can be divided by 2
      * and the other elements have no remainder when divided by 2 (are even numbers).
      *
      * `(set, set2) build((a: Int, b: Int) => a/2==0 && b%2==0) `.
      * @param f
      *   A function from the pairs to `true ` or `false `.
      * @return
      *   A subset of pairs that satisfy `f `.
      */
    def build(f: (A, B) => Boolean): Set[(A, B)] =
      (sets._1 cartesianProduct sets._2) build Function.tupled(f)

    /** Builds a set from a pair of sets (shorthand notation).
      *
      * It takes the cartesian product of the two sets, then keeps only pairs of elements that
      * return `true ` when `f ` is applied to those elements.
      *
      * Example to build a set of pairs from two initial sets, where one element can be divided by 2
      * and the other elements have no remainder when divided by 2 (are even numbers).
      *
      * `(set, set2) build((a: Int, b: Int) => a/2==0 && b%2==0) `.
      *
      * @param f
      *   A function from the pairs to `true ` or `false `.
      * @return
      *   A subset of pairs that satisfy `f `.
      */
    def |(f: (A, B) => Boolean): Set[(A, B)] = sets build f
  }

  /** Implicit functions for sets of sets.
    * @param setOfSets
    *   A set of sets.
    * @tparam A
    *   Type of the elements in the sets (within the set).
    */
  implicit class ImplSetSet[A](setOfSets: Set[Set[A]]) {

    /** Returns the union of all the sets.
      * @return
      *   The union of all the sets.
      */
    def union: Set[A] =
      if (setOfSets.nonEmpty) setOfSets.reduce(_ union _) else Set.empty

    /** Returns the intersection of all the sets.
      * @return
      *   The intersection of all the sets.
      */
    def intersection: Set[A] =
      if (setOfSets.nonEmpty) setOfSets.reduce(_ intersect _) else Set.empty
  }

}
