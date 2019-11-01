package impatient.chapter12

import org.junit.Test

/**
 * 参数（类型）推断
 */
class Sec04 {
  val  valueOfOneQuarter = (f: (Double) => Double) => f(0.25)

  @Test
  def test01(): Unit ={
    println(valueOfOneQuarter((x: Double) => 3 * x))
    //参数类型已知的情况下，可以省略
    println(valueOfOneQuarter((x) => 3 * x))
    println(valueOfOneQuarter(x => 3 * x))
    //如果参数在=>右侧只出现一次，可以用_替换它
    println(valueOfOneQuarter(3 * _))
  }

  @Test
  def test02(): Unit ={
    //val fun1 = 3 * _//错误，无法推断出类型

    val fun2 = 3 * (_: Double) //OK
    println(fun2(2))


    //这个定义很造作，不过展示了函数是如何被作为参数（刚好是那个类型）传入的
    val fun3: (Double) => Double = 3 * _//OK,因为给出了fun的类型
  }

  //给出 _ 的类型有助于将方法变成函数
  @Test
  def test03(): Unit ={
    //给出String => Int的函数
    val f1 = (_: String).length

    //给出(String, Int, Int) => String的函数
    val f2 = (_: String).substring(_: Int, _: Int)

  }
}
