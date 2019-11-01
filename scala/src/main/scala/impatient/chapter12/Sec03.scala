package impatient.chapter12

import org.junit.Test
import math._
/**
 * 带函数参数的函数
 */
class Sec03 {
  def valueOfOneQuarter(f: (Double) => Double) = f(0.25)

  /**
   * 接收函数参数的函数，所以也称为“高阶函数”
   */
  val valueOfOneQuarter2 = (f: (Double) => Double) => f(0.25)

  @Test
  def test01(): Unit ={
    println(valueOfOneQuarter(ceil))
    println(valueOfOneQuarter(ceil _))
    println(valueOfOneQuarter2(ceil))
    println(valueOfOneQuarter2(ceil _))
    println(valueOfOneQuarter2(sqrt _))
  }

  //(factor:Double)Double => Double
  def mulBy(factor: Double) = (x: Double) => factor * x

  // Double => (Double => Double)
  val mulBy2 = (factor: Double) => ((x: Double) => factor * x)

  @Test
  def test02(): Unit ={
    //mubBy(5)返回 (x:Double) => 5*x 的函数
    val quintuple = mulBy(5)
    println(quintuple(20))

    val quintuple2 = mulBy2(5)
    println(quintuple2(20))
  }
}
