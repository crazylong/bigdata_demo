package impatient.chapter12

import org.junit.Test

import scala.math.ceil

/**
 * 匿名函数
 */
class Sec02 {
  @Test
  def test01(): Unit ={
    //在Scala中你无须给每个函数命名，
    //以下是一个匿名函数，该函数将传给它的参数乘以3
    (x: Double) => 3*x

      //可以将这个函数存放到变量中
      //函数类型为Double => Double
      val triple = (x: Double) => 3 * x

      //任何以def定义的(不论在REPL、类、对象中)都是方法，不是函数
      //方法类型为(x: Double)Double
      def triple2(x: Double)=3*x
      def triple3(x: Double):Double=3*x

      //对应的方法
      //任何以def定义的都是方法，不是函数
      def triple_method(x: Double): Double = 3 * x

      //但是无须给函数命名，可以直接将它传递给另一个函数
      Array(3.14, 1.43, 2.0).map((x: Double) => 3*x)
      Array(3.14, 1.43, 2.0).map(triple)
      Array(3.14, 1.43, 2.0).map(triple2)
      Array(3.14, 1.43, 2.0).map(triple3)

      //可以将函数参数包裹在花括号中
      Array(3.14, 1.43, 2.0).map{(x: Double) => 3*x}
      //在使用中置表达式时，这种方式比较常见
      Array(3.14, 1.43, 2.0) map {(x: Double) => 3*x}
  }
}
