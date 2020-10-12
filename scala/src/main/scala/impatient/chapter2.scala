package impatient

import java.io.{IOException, InputStream}
import java.text.MessageFormat

import org.junit.Test

import scala.io.StdIn
import scala.util.Try

/**
  *
  * @author Long Qiong 
  * @create 2018/11/15
  */
class chapter2 {
  //region =========================for=======================

  @Test
  def testFor1(): Unit ={
    for(i <- 1 to 10){
      println(i)
    }
  }

  @Test
  def testFor2(): Unit ={
    val s = "Hello"
    var sum = 0
    var sum2 = "0"
    for(i <- 0 to s.length -1){
      sum += s(i)
      println(s(i))
      sum2 += s(i)
    }
    println(sum)
    println(sum2)
  }

  @Test
  def testFor3(): Unit ={
    var sum = 0
    for(ch <- "Hello"){
      sum += ch
      println(ch)
    }
    println(sum)
  }

  /**
    * break
    * Scala并没有提供break或continue语句来退出循环。有以下几种方式退出循环
    * 1. 使用Boolean型的控制变量
    * 2. 使用嵌套函数--可以从函数中return
    * 3. 使用Breaks对象中的break方法
    */
  @Test
  def testFor4(): Unit ={
    import scala.util.control.Breaks._
    //在这里，控制权的转移是通过抛出和捕获异常完成的。因此，如果时间很重要的话，你应该尽量避免使用这套机制。
    breakable{
      for(ch <- "Hello"){
        println(ch)
        if(ch == 'l') {
          break
        }
      }
    }
  }

  /**
    * 在Java中，不能在重叠的作用域内使用同名的两个局部变量；
    * 在Scala中没有这个限制，正常的遮挡规则会生效
    */
  @Test
  def testFor5(): Unit ={
    val n = "a"
    for(n <- 1 to 5)
      println(n)
    println(n)
  }



  //============================高级for循环======================
  /**
    * 多个生成器
    */
  @Test
  def testFor6(): Unit ={
    println("i\tj\n=====")
    for(i <- 1 to 3; j <- 1 to 3){
      println(i + "\t" + j)
    }
  }

  /**
    * 守卫
    */
  @Test
  def testFor7(): Unit ={
    println("i\tj\n=====")
    for(i <- 1 to 3 if i == 2; j <- 1 to 3 if i != j){
      println(i + "\t" + j)
    }
  }

  /**
    * 引入可以在循环中使用的变量
    */
  @Test
  def testFor8(): Unit ={
    println("i\tj\tk\n=====")
    for(i <- 1 to 3; k = 4 - i; j <- k to 3){
      println(i + "\t" + j + "\t" + k)
    }
  }

  /**
    * for循环的循环体以yield开始，则该循环会构造出一个集合
    */
  @Test
  def testFor9(): Unit ={
    val yields = for(i <- 1 to 10) yield i % 3
//    val yields = for(i <- 1 to 10) yield {
//      i % 3
//    }
    println(yields)
    for(y <- yields)
      println(y)
  }

  /**
    * for推导式(for comprehension)
    * 生成的集合与它的第一个生成器是类型兼容的
    */
  @Test
  def testFor10(): Unit ={

    println(0.toChar + "\t" + 1.toChar)
    println(72.toChar + "\t" + 73.toChar)
    val yields = for(c <- "Hello"; i <- 0 to 1) yield {
      (c + i).toChar
    }

    val yields2 = for(i <- 0 to 1; c <- "Hello") yield {
      (c + i).toChar
    }
    println(yields)
    println(yields2)
  }

  /**
    * 花括号
    */
  @Test
  def testFor11(): Unit ={
    println("i\tj\tk\n=====")
    for{i <- 1 to 3
        k = 4 - i
        j <- k to 3}{
      println(i + "\t" + j + "\t" + k)
    }
  }

  //endregion



  //region =========================函数=======================
//除方法外scala还支持函数，方法对对象进行操作，而函数则不是

  /**
    * 根据等号右边表达式推断返回类型
    * @param x
    * @return
    */
  def abs(x:Double) = if(x>=0) x else -x

  /**
    * 最后一个表达式的值就是函数的返回值
    * @param n
    * @return
    */
  def fac(n : Int) = {
    var r = 1
    for(i<- 1 to n) r= r*1
    r
  }

  /**
    * 对于递归函数，我们必须制定返回类型，如果没有返回类型，
    * Scala编译器就无法校验 n* fac2(n-1) 的类型是Int
    * @param n
    * @return
    */
  def fac2(n : Int): Int = if(n<=0) 1 else n*fac2(n-1)


  /**
    * 默认参数
    * @param str
    * @param left
    * @param right
    */
  def decorate(str : String, left:String="[", right:String="]"): String ={
    left + str + right
  }

  @Test
  def testDecorate(): Unit ={
    println(decorate("hello"))
    println(decorate("hello", "}"))
    println(decorate(right="}", str = "hello"))
  }

  /**
    * 变长参数
    * @param args
    * @return
    */
  def sum(args: Int*): Int ={
    var result = 0
    for(arg <- args) result += arg
    result
  }

  @Test
  def testSum(): Unit ={
    println(sum(5, 1, 3, 6,7))

    //如果sum函数被调用时传入的是单个参数，那么该参数必须是单个整数，而不是一个整数区间
    //sum(1 to 5) //错误

    sum(1 to 5: _*)// : _* 告诉编译器将 1 to 5当做参数序列处理
  }

  def recursiveSum(args: Int*):Int = {
    if(args.length==0)0
    else args.head + recursiveSum(args.tail : _*)
    //head:首元素，tail:所有其他元素的Seq序列，用: _* 来将它转换成参数序列
  }

  /**
    * 当调用变长参数且参数类型为Object的Java方法时，需要手工对基本类型进行转换
    * （对于任何Object类型的参数均如此）
    */
  @Test
  def testJavaMessageFormat(): Unit ={
    //错误
    /*var str0 =
      MessageFormat.format("The answer to {0} is {1}", "everything", 42)
    println(str0)*/

    var str1 =
      MessageFormat.format("The answer to {0} is {1}", "everything", 42.asInstanceOf[AnyRef])
    println(str1)
  }
  //endregion



  //region =========================过程=======================

  def box(s:String){
    println(s)
  }

  def box2(s:String):Unit={
    println(s)
  }

  //endregion


  //region =========================懒值=======================

  /**
    * 可以把懒值当做介于val和def的中间状态。
    */
  @Test
  def testLazy(): Unit ={
    /*
    //在word定义时取值
    val words1 = scala.io.Source.fromFile("F://words.txt").mkString
    println(words1)

    //在word使用时取值
    lazy val words2 = scala.io.Source.fromFile("F://words.txt").mkString
    println(words2)

    //在调用时取值
    def words3 = scala.io.Source.fromFile("F://words.txt").mkString
    println(words3)*/


    //写错文件路径
    /*val words1 = scala.io.Source.fromFile("F://words2.txt").mkString
    println(words1)*/

    /*lazy val words2 = scala.io.Source.fromFile("F://words2.txt").mkString
    println(words2)*/

    def words3 = scala.io.Source.fromFile("F://words2.txt").mkString
    println(words3)
  }
  //endregion

  //region =========================异常=======================

  /**
    * throw的表达式有特殊的类型Nothing。
    * 如果一个分支的类型是Nothing，那么if/else表达式的类型就是另一个分支的类型。
   * 第一个分支类型是Double，第二个分支类型是Nothing，整个if/else分支类型是Double
    * @param x
    * @return
    */
  def exceptionIf(x: Double) ={
    if(x>=0){
      math.sqrt(x)
    } else {
      throw new IllegalArgumentException("x shout not be negative")
    }
  }

  import java.net._
  import javax.imageio._
  import javax.swing._

  def process(u: URL) {
    val img = ImageIO.read(u)
    JOptionPane.showMessageDialog(null, null, null, 0, new ImageIcon(img))
  }

  def process(in: InputStream) {
    var bytes = -1
    var next = 0
    while (next != -1) {
      next = in.read()
      bytes += 1
    }
    println(bytes + " bytes")
  }

  /**
    * 捕获异常采用模式匹配语法
    * 和Java一样，更通用的异常应该排在更具体的异常之后
    * 如果不需要使用捕获的异常对象，可以使用_来替代变量名
    */
  @Test
  def exceptionSyntax(): Unit ={
    var url = new URL("http://horstmann.com/fred-tiny.gif");
    var url2 = new URL("http://horstmann.com/cay-tiny.gif");
    try {
      process(url)
//      process(new URL("http://horstmann.com/cay-tiny.gif"))
//      process(new URL("http://horstmann.com/fred.gif"))
    } catch {
      case _: MalformedURLException => println("Bad URL: " + url)
      case ex: IOException => ex.printStackTrace()
    }
  }

  /**
    * finally语句不论process函数是否抛出异常都会被执行
    * 如果in.close()抛出异常，这样一来异常跳出当前语句，废弃并替代掉所有先前的异常（跟Java一样）
    */
  def exceptionTryFinally(): Unit ={
    val in = new URL("http://horstmann.com/fred.gif").openStream()

    try{
      process(in)
    } finally {
      in.close()
    }
  }

  import resource._

  /**
    * Scala并没有与Java的try-with-resource想对应的语法结构。
    * 可以考虑使用scala-ARM类库(http://jsuereth.com/scala-arm)
    * 没跑成功
    */
  @Test
  def testResource(): Unit = {
    for (input <- managed(new java.io.FileInputStream("F://words.txt"));
         output <- managed(new java.io.FileOutputStream("F://words.txt"))) {
      val buffer = new Array[Byte](512)

      def read(): Unit = input.read(buffer) match {
        case -1 => ()
        case n => output.write(buffer, 0, n); read()
      }
    }
  }


//  import java.nio.file._
//  @Test
//  def testResource2(): Unit = {
//
//
//
//    for  (in<- resource(Files.newBufferedReader(inPath));
//          out <- resource(Files.newBufferedWriter(outPath)))  {
//
//    }
//  }


  /**
    * Try类被设计用于处理可能会以异常失败的计算
    */
  @Test
  def testTry(): Unit ={
    val result =
      for(a <- Try{StdIn.readLine("a: ").toInt};
          b <- Try{StdIn.readLine("b: ").toInt})
        yield a/b
  }

  //endregion



  //region =========================练习=======================
  def getSignum(x:Int):Int = {
      if(x>0) 1
      else if(x<0) -1
      else 0
  }

  @Test
  def testSignum(): Unit ={
    print(getSignum(-5))
  }


  @Test
  def testEmpty(): Unit ={
    val a = {}
    println(a)
    println(a.getClass)
    println(a.isInstanceOf[Unit])
  }

  /**
    * 设置 x = {}时， x = y = 2 合法
    */
  @Test
  def test01(): Unit ={
    var x = {}
    var y = 1 : Int

    x = y = 2
  }

  def countDown(x : Int): Unit ={
    for(i <- Range(x, 0, -1)){
      println(i)
    }
  }

  @Test
  def testCountDown: Unit ={
    countDown(5)
  }

  def countUnicode(str:String): Unit ={
    var result = 1 : Long
    for(s <- str){
      result *= s
    }
    println(result)
  }

  def recurseCount(s: String): Long ={
    if(s.size > 0)
      recurseCount(s.tail)*s.head.toLong
    else
      1
  }

  @Test
  def testCountUnicode(): Unit ={
    countUnicode("Hello")

    println("Hello".foldLeft(1L)(_ * _.toLong))

    println(recurseCount("Hello"))
  }

  //endregion
}
