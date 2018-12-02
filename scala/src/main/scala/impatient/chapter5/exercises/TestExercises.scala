package impatient.chapter5.exercises

import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/12/2
  */
class TestExercises {
  @Test
  def testBankAccount(): Unit ={
    val a = new BankAccount()
    try {
      a.deposit(100)
      println(a.balance)
      a.withdraw(20)
      println(a.balance)
      a.withdraw(100)
      println(a.balance)
    } catch{
      case e: Exception => println(e.getMessage)
    }
  }

  @Test
  def testTime(): Unit ={
    val t1 = new Time(5, 5)
    val t2 = new Time(5, 10)
    println(t1.before(t2))
  }

  @Test
  def testStudent(): Unit ={
    val stu = new Student
    stu.setId(100)
    stu.setName("Long")
    println(stu)

    val stu2 = new Student
    stu2.id = 10
    stu2.name = "oo"
//    stu2.id_(20)
//    stu2.name_("oo")
    println(stu2)
  }

  @Test
  def testPerson(): Unit ={
    val p = new Person(-10)
    p.showAge()
  }

  @Test
  def testCar(): Unit ={
    val c1 = new Car("a", "b", 5, "c")
    println(c1)
    val c2 = new Car("aa", "bb")
    println(c2)
    val c3 = new Car("aaa", "bbb", 6)
    println(c3)
    val c4 = new Car("aaaa", "bbbb", carNo = "cccc")
    println(c4)
  }
}
