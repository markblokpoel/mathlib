package mathlib.demos

import mathlib.set.SetTheory.sum

object SetTest {
  def main(args: Array[String]): Unit = {
    def len(s: String): Int = s.length
    println(
      sum(Set("a","b","aa","bb","ccc"), len _) == 9
    )
  }
}
