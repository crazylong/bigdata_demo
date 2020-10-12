package impatient.chapter12

import org.junit.Test
import math._
/**
 * 带函数参数的函数
 */
class Sec03 {
  //接受另一个函数作为参数的函数
  //参数可以是任何接受Double并返回Double的函数
  //ps: 按照前面一节的描述def定义的都是方法，方法类型为 (f: Double => Double)Double
  def valueOfOneQuarter(f: (Double) => Double) = f(0.25)

  /**
   * 接收函数参数的函数，所以也称为“高阶函数”
   * 函数类型为 (Double => Double)=>Double
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

  //高阶函数产出另一个函数
  //(factor:Double)Double => Double
  def mulBy(factor: Double) = (x: Double) => factor * x

  def mulBy3(factor: Double): Double=>Double = (x: Double) => factor * x

  //mulBy2有一个factor参数，返回一个Double=>Double函数
  //因此mulBy2的类型为 Double => (Double => Double)
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
