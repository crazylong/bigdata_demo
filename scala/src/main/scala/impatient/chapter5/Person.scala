package impatient.chapter5

/**
  *
  * @author Long Qiong 
  * @create 2018/12/1
  */
class Person {
  //Scala对每个字段都提供getter和setter方法
  //定义公有字段
  //Scala生成面向JVM的类，其中有一个私有的age字段以及相应的getter和setter方法。
  //这两个方法是公有的，因为我们没有将age声明为private。
  // （对私有字段而言，getter和setter方法也是私有的）
  var age = 0

  //getter和setter方法也是私有的
  private var name = ""

  //scalac Person.scala
  //javap -private Person

  //重新定义getter和setter方法
  private var privateSex = 0
  def sex = privateSex   //getter

  def sex_(newValue : Int)={//setter
    if(newValue > 3) privateSex = 1
  }

  //如果字段是私有的，则getter和setter方法也是私有的
  //如果字段是val， 则只有getter方法被生成
  //如果你不需要任何getter或setter，可以将字段声明为private[this]


  val school = ""

  private[this] val score = 0
}
