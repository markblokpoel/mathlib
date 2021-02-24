package mathlib.demos

import mathlib.graph.{Node, WUnDiEdge}
import mathlib.set.SetTheory._

object Coherence {

//  def combine[D, R](f1: D => Option[R], f2: D => Option[R])(el: D): Option[R] = {
//    if(f1(el).isDefined) f1(el)
//    else if(f2(el).isDefined) f2(el)
//    else None
//  }
//
//  def combine[D, R](f1: D => Option[R], f2: D => R)(el: D): R = f1(el).getOrElse(f2(el))
//
//  implicit class ImplFun[D, R](f1: D => Option[R]) {
//    def ~~(f2: D => Option[R])(el: D): Option[R] = combine(f1, f2)(el)
//    def ~~(f2: D => R)(el: D): R = combine(f1, f2)(el)
//  }
//
//  case class Variable[T](label: String, domain: Set[T], value: Option[T]) {
//    def isAssigned: Boolean = value.isDefined
//    def isUnassigned: Boolean = value.isEmpty
//    def <=(_value: T): Variable[T] = Variable(label, domain, Some(_value))
//    def possibleAssignments: Map[Variable[T], T] = (for(opt <- domain) yield this -> opt).toMap
//    def possibleAssignment(other: Variable[T]): Map[Variable[T], T] = {
//      val a1 = possibleAssignments
//      val a2 = other.possibleAssignments
//
//      for(kv1 <- a1; kv2 <- a2) yield {
//        val bla = kv1
//      }
//    }
//  }
//
//  def coherence(beliefs: Set[Node[Belief]],
//                positiveConstraints: Set[WUnDiEdge[Node[Belief]]],
//                negativeConstraints: Set[WUnDiEdge[Node[Belief]]]): Map[Belief, Boolean] = {
//
//
//
//  }



//  type UnEdge[T] = Set[T]
//  type DiEdge[T] = List[T]
//  type WeUnEdge[T] = (Set[T], Double)
//  type WeDiEdge[T] = (List[T], Double)
//
//  type Belief = String
//
//  def foundationalCoherence(beliefs: Set[Belief],
//                            positiveConstraints: Set[WeUnEdge[Belief]],
//                            negativeConstraints: Set[WeUnEdge[Belief]],
//                            data: Set[Belief],
//                            dataAssignment: Belief => Boolean
//                           ): (Double, Option[Belief => Boolean]) = {
//    def allValueAssignments(set: Set[Belief], partialValueAssignments: Set[Belief => Option[Boolean]]): Set[Belief => Option[Boolean]] = {
//      if(set.isEmpty) partialValueAssignments
//      else {
//        val next = set.head
//        val rest = set.tail
//        def ft(b: Belief): Option[Boolean] = if(b == next) Some(true) else None
//        def ff(b: Belief): Option[Boolean] = if(b == next) Some(false) else None
//
//        allValueAssignments(rest, partialValueAssignments.flatMap(pva => Set(pva ~~ ft, pva ~~ ff)))
//      }
//    }
//
//    def coh(valueAssignment: Belief => Boolean): Double = {
//      positiveConstraints.map(con => {
//        val b1 = con._1.head
//        val b2 = con._1.tail.head
//        if(valueAssignment(b1) == valueAssignment(b2)) con._2
//        else 0.0
//      }).sum +
//      negativeConstraints.map(con => {
//        val b1 = con._1.head
//        val b2 = con._1.tail.head
//        if(valueAssignment(b1) != valueAssignment(b2)) con._2
//        else 0.0
//      }).sum
//    }
//
//    val bva = allValueAssignments(beliefs \ data, Set((_: Belief) => None))
//      .map(f => combine(f, dataAssignment)(_))
//      .argMax(coh)
//
//    if(bva.isDefined) (coh(bva.get), Some(bva.get))
//    else (0.0, None)
//  }

}
