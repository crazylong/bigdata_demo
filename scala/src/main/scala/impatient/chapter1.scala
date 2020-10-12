package impatient

import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/11/12
  */
class chapter1 {
  @Test
  def companionObjectTest() ={
    val i = BigInt.probablePrime(100, scala.util.Random)
    println(i.toString().length)
    println(i.toString(36))
  }

  /**
    * 使用伴生对象的apply方法是scala中构建对象的常用手法
    */
  @Test
  def applyTest(): Unit ={
    val s = "Hello"
    //()操作符的重载形式，它背后的实现原理是一个名为apply的方法
    println(s(4))
    // s(4)为s.apply(4)的简写形式
    println(s.apply(4))

    BigInt("123456789")

    Array(1, 4, 9, 16)

    //偶尔()表示法会跟另一个scala特性--隐式参数--相冲突
    //"Bonjour".sorted(3)

    //("Bonjour".sorted)(3)

    "Bonjour".sorted.apply(3)
  }

  @Test
  def stringTest(): Unit ={
    val s = "Hello Scala, Love U";
    println(s.take(2))
    println(s.drop(4))
    println(s.takeRight(2))
    println(s.dropRight(4))
  }
}
