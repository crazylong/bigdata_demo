package impatient.chapter12

import org.junit.Test

/**
 * 一些有用的高阶函数
 */
class Sec05 {
  /**
   * 打印一个三角形
   */
  @Test
  def test01(): Unit ={
    (1 to 9).map("*" * _).foreach(println(_))

    (1 to 9).map((x) => "*" * x).foreach(x => println(x))

    (1 to 9).filter(_ % 2==0).foreach(println(_))

    //reduceLeft接收一个二元函数
    //等同于 1 * 2 *3*4*5
    //准确的说是 ((((1*2)*3)*4)*5)
    val result = (1 to 5).reduceLeft(_ * _)
    println(result)

    val result2 = (1 to 5).reduceLeft((x, y) => x * y)
    println(result2)

    "Mary had a little lamb".split(" ").sortWith(_.length < _.length).foreach(println(_))

  }
}
