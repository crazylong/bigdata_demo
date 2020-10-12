package impatient.chapter8

abstract class Person2 {
  def id : Int//没有方法体，这是一个抽象方法

  //没有初始化--这是一个带有抽象的getter方法的抽象字段
  val id2: Int

  //带有抽象的getter和setter方法
  var name: String
}

//val重写抽象的def
class Student(override val id: Int, val id2: Int, var name: String) extends Person2
