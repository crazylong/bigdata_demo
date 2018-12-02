package impatient.chapter5.exercises

/**
  *
  * @author Long Qiong 
  * @create 2018/12/2
  */
class Person(var age:Int) {
  if(age < 0) age = 0
  def showAge(): Unit ={
    println(age)
  }
}
