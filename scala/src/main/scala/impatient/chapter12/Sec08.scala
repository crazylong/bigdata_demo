package impatient.chapter12

import org.junit.Test

/**
 * 柯里化
 */
class Sec08 {
  val mul = (x: Int, y: Int) => x * y

  //函数接受一个参数，生成另一个接受单个参数的函数
  val mulOneAtTime = (x: Int) => ((y: Int) => x * y)

  //柯里化方法
  //(x:Int)(y:Int)Int
  def mulOneAtTime2(x: Int)(y: Int) = x * y

  //柯里化函数
  //Int => (Int => Int)
  val mulOneAtTime3 = (x: Int) => (y: Int) => x * y

  @Test
  def test01(): Unit ={
    //mulOneAtTime(6)的结果是 (y: Int) => 6 * y，而这个函数又被应用到7
    println(mulOneAtTime(6)(7))
  }


  /**
   * 用柯里化把某个函数参数单拎出来，以提供更多用于类型推断的信息
   */
  @Test
  def test02(): Unit ={
    val a = Array("Hello", "World")
    val b = Array("hello", "world")

    //def corresponds[B](that: Seq[B])(p: (A, B) => Boolean): Boolean
    //比较两个序列是否在某个比对条件下相同
    a.corresponds(b)(_.equalsIgnoreCase(_))

    //that和p是分开的两个柯里化参数。类型推断器可以分析出B出自that的类型，
    //因此就可以利用这个信息来分析作为参数p传入的参数
    //拿本例来说，that是一个String类型的序列。
    // 因此前提函数的类型为(String, String) => Boolean。
    // 有了这个信息，编译器就可以接受_.equalsIgnoreCase(_)作为
    //(a: String, b:String)=>a.equalsIgnoreCase(b)的简写了
  }
}
