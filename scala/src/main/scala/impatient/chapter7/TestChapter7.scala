package impatient.chapter7

import org.junit.Test

import scala.collection.mutable

class TestChapter7 {
  @Test
  def testEmployee(): Unit ={
    //com.horstmann.impatient.Employee
    //impatient.chapter7.com.horstmann.impatient.Employee
   // com.horstmann.impatient.Main
    //impatient.chapter7.

    val emp  = new impatient.chapter7.Employee
    val counter = new net.bigjava.Counter
  }

  /**
   * 包对象
   */
  @Test
  def testPackageObject(): Unit ={
    println(people.defaultName)

    import impatient.chapter7.people.Person
    val person = new Person
    println(person.name)
  }

  /**
   * 引入
   * 引入类火对象的所有成员
   * 类似java的import static
   */
  @Test
  def testImport(): Unit ={
    import java.awt.Color._
    val c1 = RED //Color.RED
    val c2 = decode("#ff0000") //Color.decode
  }

  /**
   * 重命名和隐藏方法
   */
  @Test
  def testSelector(): Unit ={
    import java.awt.{Color, Font}
    //重命名
    //import java.util.{HashMap => JavaHashMap}
    //import scala.collection.mutable._

    //import java.util.{HashMap => _}
    import java.util.{HashMap => _, _}

    //val hm = new HashMap

    val tm = new TreeMap
  }
}
