package impatient.chapter14

import org.junit.Test

class sec01 {
  @Test
  def test01(): Unit = {
    var sign = 0
    for (ch <- "+-!*") {
      ch match {
        case '+' => sign = 1
        case '-' | '*' => sign = -1
        case _ => sign = 0
      }
      println(sign)
    }
  }
}
