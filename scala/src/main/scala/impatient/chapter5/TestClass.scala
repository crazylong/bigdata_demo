package impatient.chapter5

import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/12/1
  */
class TestClass {
  @Test
  def testCounter(): Unit ={
    val myCounter = new Counter
    myCounter.increment()
    //myCounter.increment
    println(myCounter.current)
    //调用无参方法（比如current）时，可以写上圆括号，也可以不写
    //我们认为对于改值器方法（即改变对象状态的方法）使用()，
    // 而对于取值器方法（不会改变对象状态的方法）去掉()是不错的风格
  }

  @Test
  def testPerson(): Unit ={
    var fred = new Person
    println(fred.age) //将调用方法fred.age()
    fred.age = 21     //将调用fred.age_=(21)
  }
}
