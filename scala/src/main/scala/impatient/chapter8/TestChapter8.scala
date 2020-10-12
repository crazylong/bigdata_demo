package impatient.chapter8

import org.junit.Test

class TestChapter8 {
  @Test
  def testOverride(){
    val s = new SecretAgent("aa", 10)
    println(s.toString)
    //s.
  }

  @Test
  def testAbstract(): Unit ={
    val s = new Student(1, 2, "3")
    println(s.name)

    val s2 = new Person2 {
      override def id: Int = 11

//      override val id2: Int = 22
//      override var name: String = "33"
        val id2: Int = 22
        var name: String = "33"
    }

    println(s2.name)
  }

  @Test
  def testCreature(): Unit ={
    var c : Creature = new Creature
    println(c.env.length)

    val a : Ant = new Ant
    println(a.env.length)
  }


  @Test
  def testUnit(): Unit ={
    printAny("Hello")
    printUnit("Hello")

    show()
    show(3)
    show(3, 4, 5)
  }

  def printAny(x: Any) {println(x)}
  def printUnit(x: Unit) {println(x)}

  def show(o: Any){println(s"${o.getClass}:$o")}


  /**
   * 值类测试
   */
  @Test
  def testValueClass(): Unit ={
    //val mt = new MilTime(1230)
    val mt = MilTime(1230)
    println(mt)
    //println(mt*2)//错误
    println(mt.minutes)
    println(mt.minutes*2)

  }

}
