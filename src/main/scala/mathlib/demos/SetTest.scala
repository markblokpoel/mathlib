package mathlib.demos

import mathlib.set.SetTheory._

object SetTest {
  def main(args: Array[String]): Unit = {
    def len(s: String): Int = s.length
    def len2(s: (String, String)): Int = (s._1 + s._2).length
    def isEven(s: String): Boolean = (len(s) % 2) == 0

    Seq(
      (sum(Set("a","b","aa","bb","ccc", "ddd"), len _), 12),
      (product(Set("a", "b", "aa", "bb", "ccc", "ddd"), len _), 1*1*2*2*3*3),
      (sum(Set(("a", "b"), ("aa", "bb"), ("ccc", "ddd")), len2 _), 2+4+6),
      (product(Set(("a", "b"), ("aa", "bb"), ("ccc", "ddd")), len2 _), 2*4*6),
      (sum(Set("a","b","aa","bb","ccc", "ddd"), isEven _, len _), 4)
    ).foreach(qa => println(f"${qa._1 == qa._2} <- ${qa._1} == ${qa._2}"))
  }
}
