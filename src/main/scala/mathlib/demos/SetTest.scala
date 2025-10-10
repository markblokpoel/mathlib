package mathlib.demos

import mathlib.set.SetTheory._

object SetTest {
  def main(args: Array[String]): Unit = {
    def len(s: String): Int = s.length
    def len2(s: (String, String)): Int = (s._1 + s._2).length
    def isEven(s: String): Boolean = (len(s) % 2) == 0
    def sameLen(pair: (String, String)): Boolean = pair._1.length == pair._2.length

    Seq(
      (sum(Set("a","b","aa","bb","ccc", "ddd"), len _), 12),
      (product(Set("a", "b", "aa", "bb", "ccc", "ddd"), len _), 1*1*2*2*3*3),
      (sum(Set(("a", "b"), ("aa", "bb"), ("ccc", "ddd")), len2 _), 2+4+6),
      (product(Set(("a", "b"), ("aa", "bb"), ("ccc", "ddd")), len2 _), 2*4*6),
      (sum(Set("a","b","aa","bb","ccc", "ddd"), isEven _, len _), 4),
      (product(Set("a","b","aa","bb","cc", "ddd"), isEven _, len _), 8),
      (sum(Set(("a","aa"),("b","bb"), ("cc", "cc"), ("ddd", "ddd")), sameLen _, len2 _), (2+2)+(3+3)),
      (product(Set(("a","aa"),("b","bb"), ("cc", "cc"), ("ddd", "ddd")), sameLen _, len2 _), (2+2)*(3+3)),
    ).foreach(qa => println(f"${qa._1 == qa._2} <- ${qa._1} == ${qa._2}"))


    val s = Set(1,2,3)
    Seq(
      s.combinations3,
      s x s x Set("a","b") x s,
      s.permutations3,
      (s x s x s)
    ).foreach(println)
  }
}
