package impatient.chapter12

import org.junit.Test
import scala.math._

class Sec01 {
  /**
   * 作为值的函数
   */
  @Test
  def test01(): Unit ={
    val num = 3.14//类型Double

    //fun 设为ceil函数
    //ceil函数后的_意味着你确实指的是这个函数，而不是恰巧忘记了给它送参数
    //类型(Double) => Double，即接收并返回Double函数
    //从技术上讲，_将ceil方法变成了函数。
    //在Scala中你没法随意操纵方法，只能操作函数。
    //对比而言，ceil方法的类型是(Double)Double，中间没有箭头
    val fun = ceil _


    //在一个预期需要函数的上下文里使用方法名时，_后缀不是必需的。
    val f: (Double) => Double = ceil //不需要下划线
    val f2: (Double) => Double = ceil _ //有下划线也可以



    //ceil方法是scala.math这个包对象的方法。如果你有一个来自类的方法，将它变成函数的方式略微不同：
    //1.这是一个类型为(String, Int) => Char 的函数
    val f3 = (_: String).charAt(_: Int)

    //2.或者，也可以指定函数类型而不是参数类型
    val f4: (String, Int) => Char = _.charAt(_)


    //你能对函数做些什么呢？两件事：
    //1.调用它；
    val t1 = fun(num)
    //2.传递它，存放在变量中，或者作为参数传递给另一个函数
    val t2 = Array(3.14, 1.42, 2.0).map(fun)
  }
}
