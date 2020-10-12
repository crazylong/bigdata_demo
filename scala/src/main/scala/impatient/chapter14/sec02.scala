package impatient.chapter14

import org.junit.Test

/**
 * 守卫
 */
class sec02 {
  @Test
  def test01(): Unit = {
    var digit = 0
    var sign = 0
    for (ch <- "+-3!") {
        ch match {
          case _ if (Character.isDigit(ch)) => digit = Character.digit(ch, 10)
          case '+' => sign = 1
          case '-' => sign = -1
          case _ => sign = 0
        }

        println("digit:" + digit + ",sign:" + sign)
    }
  }
}
