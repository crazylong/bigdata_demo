package impatient.chapter14

import org.junit.Test

/**
 * 模式中的变量
 * 如果case关键字后面跟的是一个变量名，那么匹配的表达式会被赋值给那个变量
 */
class sec03 {
  @Test
  def test01(): Unit ={
    val str = "+!-3"
    var sign = 0
    var digit = 0
    for(i <- str.indices){
      str(i) match {
        case '+' => sign = 1
        case '-' => sign = -1
        case ch => digit = Character.digit(ch, 10)
      }
      //print(ch)
    }
  }

  /**
   * 可以将case _看作这个特性的一个特殊情况，只不过变量名是_
   */
  @Test
  def test02(): Unit ={
    val str = "+!-3"
    var sign = 0
    var digit = 0
    for(i <- str.indices){
      str(i) match {
        case '+' => sign = 1
        case '-' => sign = -1
        case ch if Character.isDigit(ch) => digit = Character.digit(ch, 10)
        case _ => sign = 0
      }
      println(digit)
    }
  }
}
