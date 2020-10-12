package impatient.chapter5

import scala.beans.BeanProperty

/**
  *在事项属性时你有如下四个选择：
  * 1，var foo: Scala自动合成一个getter方法和一个setter方法
  * 2, val foo:Scala自动合成一个getter方法
  * 3，由你来定义foo 和 foo_= 方法
  * 4，由你来定义foo 方法
  *
  * 在Scala中，你不能实现只写属性
  * （即带有setter但不带getter的属性）
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

  def sex = privateSex //getter

  def sex_(newValue: Int) = {
    //setter
    if (newValue > 3) privateSex = 1
  }


  //如果字段是val， 则只有getter方法被生成
  //会生成一个私有的final字段和一个getter方法
  val school = ""

  //如果字段是私有的，则getter和setter方法也是私有的
  private var score1 = 0


  //对象私有
  //如果你不需要任何getter或setter，可以将字段声明为private[this]
  private[this] val score2 = 0

  private val score3 = 0

  //方法可以访问该类的所有对象的私有字段
  def isLess(other: Person) = score1 < other.score1 //访问另一个对象的私有字段

  //private[this] 只能访问当前对象的私有字段
  //def isLess2(other: Person) = score2 < other.score2

  //Scala允许你将访问权限赋予指定的类
  //private[类名]修饰符可以定义仅有指定类的方法可以访问给定的字段
  //这里的类名必须时当前定义的类，或者是包含该类的外部类


  //Bean 属性
  @BeanProperty var last_name: String = _
  //将会生成四个方法：
  //1. name: String
  //2. name_ =(newValue:String):Unit
  //3. getName():String
  //4. setName(newValue:String):Unit

  //如果你以主构造器参数的方式定义了某字段，并且你需要JavaBeans版的getter和setter方法，
  //像如下这样给构造器参数加上注解即可：
  //class Person(@BeanProperty var name: String)

}