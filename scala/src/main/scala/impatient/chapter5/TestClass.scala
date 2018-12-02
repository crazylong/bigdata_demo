package impatient.chapter5


import java.util.Properties

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

  @Test
  def testPerson2(): Unit ={
    var p1 = new Person2    //主构造器
    var p2 = new Person2("Lily")//第一个辅助构造器
    val p3 = new Person2("Lucy", 10)//第二个辅助构造器
  }

  @Test
  def testNetwork(): Unit ={
    val chatter = new Network
    val myFace = new Network
    //在Scala中，每个实例都有他自己的Member类，就和它们有自己的members字段一样。
    //也就是说，chatter.Member和myFace.Member是不同的两个类
    //这和Java 不同，在Java中的内部类从属于外部类
    //构建一个新的内部类：
    //1.Scala:
    //  new chatter.Member
    //2.Java:
    //  chatter.new Member()

    //拿我们的网络示例来讲，你可以在各自的网络中添加成员，但不能跨网添加成员
    val fred = chatter.join("fred")
    val wilma = chatter.join("wilma")
    fred.contacts += wilma //OK

    val barney = myFace.join("barney")
    //fred.contacts += barney //不可以这样做--不能将一个myFace.Member添加到chatter.Member元素缓冲当中
    //如果你不希望是这种效果，则有两种解决方式：
    //1.将Member类移到别处。一个不错的位置是Network的伴生对象
    //2.使用类型投影(type projection) Network#Member，其含义是“任何Network的Member”
  }
}
