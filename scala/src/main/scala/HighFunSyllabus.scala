import org.junit.Test

/**
  * 高阶函数
 *
  * @author Long Qiong 
  * @create 2018/10/7
  */
class HighFunSyllabus {

  def play(x:Int=>Unit): Unit ={
    x(100)
  }

  def f1(x:Int): Unit ={
    println(x)
  }

  @Test
  def test1(): Unit ={
    play(f1)
  }

  def play1 = 10 //这是一个函数，同时也是一个对象
  val play2 = 10 //这是一个函数，同时也是一个对象

  @Test
  def test2(): Unit ={
    println(play2)
    println(play1)
  }

  //匿名函数
  val play3 = ()=>20
  val play4=(a:Int, b:Int)=>30

  @Test
  def test3(): Unit ={
    println(play3)
    println(play3())
    println(play4)
    println(play4(1,2))
  }


}
