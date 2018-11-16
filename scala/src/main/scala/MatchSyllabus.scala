import org.junit.Test

/**
  * 模式匹配
  *
  * @author Long Qiong 
  * @create 2018/10/7
  */
class MatchSyllabus {

  @Test
  def test1(): Unit ={
    var result = 0;
    val op : Char = '-'

    op match {
      case '+' => result = 1
      case '-' => result = -1
      case _ => result = 0
    }
    println(result)
  }

  //守卫
  @Test
  def test2(): Unit ={
    for (ch <- "+-3!") {
      var sign = 0
      var digit = 0

      ch match {
        case '+' => sign = 1
        case '-' => sign = -1
        case _ if ch.toString.equals("3") => digit = 3
        case _ => sign = 0
      }
      println(ch + " " + sign + " " + digit)
    }
  }

  //模式匹配中的变量
  @Test
  def test3(): Unit ={
    val str = "+-3!"
    for (i <- str.indices) {
      var sign = 0
      var digit = 0

      str(i) match {
        case '+' => sign = 1
        case '-' => sign = -1
        case value if Character.isDigit(str(i)) => {
          digit = Character.digit(value, 10)
          println(digit)
        }
        case _ =>
      }
      println(str(i) + " " + sign + " " + digit)
    }
  }

  //类型模式
  @Test
  def test4(): Unit = {
    val a = 8
    val obj = if (a == 1) 1
      else if (a == 2) "2"
      else if (a == 3) BigInt(3)
      else if (a == 4) Map("aa" -> 1)
      else if (a == 5) Map(1 -> "aa")
      else if (a == 6) Array(1, 2, 3)
      else if (a == 7) Array("aa", 1)
      else if (a == 8) Array("aa")
    val r1 = obj match {
      case x: Int => x
      case s: String => s.toInt
      case BigInt => -1 //不能这么匹配
      case _: BigInt => Int.MaxValue
      case m: Map[String, Int] => "Map[String, Int]类型的Map集合"
      case m: Map[_, _] => "Map集合"
      case a: Array[Int] => "It's an Array[Int]"
      case a: Array[String] => "It's an Array[String]"
      case a: Array[_] => "It's an array of something other than Int"
      case _ => 0
    }
    println(r1)
  }

  //变量声明中的匹配模式
  @Test
  def test5(): Unit ={
    val(q, r) = BigInt(10) /% 3
    println(q)
    println(r)

    println(10 / 3)
    println(10 % 3)
  }

  //for表达式中的模式
  @Test
  def test6(): Unit ={
    import scala.collection.JavaConverters._
    for ((k, v) <- System.getProperties.asScala)
      println(k + " -> " + v)

    for ((k, "") <- System.getProperties.asScala)
      println(k)

    println("============等价============")
    for ((k, v) <- System.getProperties.asScala if v == "")
      println(k)
  }


  //样例类
  @Test
  def test7: Unit ={
    for (amt <- Array(Dollar(1000.0), Currency(1000.0, "EUR"), Nothing)) {
      val result = amt match {
        case Dollar(v) => "$" + v
        case Currency(_, u) => u
        case Nothing => ""
      }
      println(amt + ": " + result)
    }
  }

  //偏函数
  @Test
  def test8: Unit ={
//    println(f("+"))
    println(f(2))
    println(f(2))
    println(f.isDefinedAt(2))
    println(f.isDefinedAt(3))
   // println(f(3))
   // println(f("*"))
  }

  //偏函数，接收Char，返回Int
  val f: PartialFunction[Char, Int] = {
    case '+' => 1
    case '-' => -1
    case 2 => 2
  }
}

//样例类
abstract class Amount
case class Dollar(value: Double) extends Amount
case class Currency(val value: Double, unit: String) extends Amount
case object Nothing extends Amount
