import org.junit.Test

import scala.collection.mutable.ArrayBuffer

/**
  *
  * @author Long Qiong 
  * @create 2018/10/6
  */
class ScalaTest {
  @Test
  def test_for()={
    for(i<-1 to 3;j<-1 to 3){
      print(i*j+" ")
    }
    println()
  }



  @Test
  def test_forUntil()={
    for(i<-1 until 3){
      print(i + " ")
    }
  }

  //守卫，也称之为：保护式，可以实现类型Continue的功能
  @Test
  def test_for2()={
    for(i<-1 to 3 if i !=2){
      print(i + " ")
    }
  }

  //yield返回
  @Test
  def test_for_yield()={
    val for1 = for(i<-1 to 10) yield i
    print(for1)
  }

  //花括号代替小括号
  @Test
  def test_for3() ={
    for{
      i <- 1 to 3
      j = 4-i}
      print(i*j + " ")
  }

  def play1(a1:Int,a2:String):String ={
    "Hello"
    //return "Hello";
  }

  def play2(a1:Int,a2:String) ={
    5
    5.5
    5.5f
    true
    //"Hello"
    //return "Hello";
  }

  def play3={
    10
  }

  def play4()={
    20
  }

  @Test
  def test_fun1(): Unit ={
    println(play3)
    //println(play3())
    println(play4)
    println(play4())
  }

  //匿名函数
  val play= () => 3

  //定义方法的几中形式
  def fun1():Unit={

  }
  def fun2() ={
    ()
  }
  def fun3 {
    return 10
  }
  def fun4(){
    return 10
  }

  @Test
  def test_fun2(): Unit ={
    print(fun3)
  }

  def init() ={
    println("init方法执行")
    "嘿嘿嘿，我来了"
  }

  @Test
  def test_lazy(): Unit ={
    lazy val msg = init();
    println("lazy方法没有执行")
    println(msg)
  }

  @Test
  def testTuple2(): Unit ={
    var tuple2 : (String, Int) = ("a", 1)
    println(tuple2)
  }

  @Test
  def testFormat(): Unit ={
    val sql = "hi, %s, fuck you %s".format("xiao ming", "haha")
    println(sql)
    println("%1$s-%2$s-%3$s".format("spark","scala","ml"))
  }
  //1 to 10

  @Test
  def testGroup(): Unit ={
    var source = ArrayBuffer[Map[String, Int]]()
    source += Map(("a"-> 1), ("b"->2), ("c"->3))
    source += Map(("a"-> 1), ("b"->2), ("c"->4))
    source += Map(("a"-> 1), ("b"->22), ("c"->4))

    var groups: Map[(Option[Int], Option[Int]), ArrayBuffer[Map[String, Int]]] = source.groupBy(s => (s.get("a"), s.get("b")))

    println(groups)

    //groups.map(s => println(s._2(0)))
    var map = groups.map(s => s._2(0))
    println(map)
  }
}
