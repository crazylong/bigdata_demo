package impatient

import org.junit.Test

class chapter3 {
  def testArray(): Unit ={
    //10个整数的数组，所有元素初始化为0
    val nums = new Array[Int](10)

    //所有元素初始化为null
    val a = new Array[String](10)

    //提供初始值，不要用new
    val s = Array("Hello", "World")

    //使用( )而不是[]来访问元素
    s(0) = "Goodbye"

    //在JVM中就是一个int[]
  Array(2, 3, 5, 7)

  }

  import scala.collection.mutable.ArrayBuffer
  @Test
  def testArrayBuffer(): Unit ={
    val b = ArrayBuffer[Int]()
    //或者
    var b2 = new ArrayBuffer[Int]
    b+=3//ArrayBuffer(3)
    println(b)

    b+=(4, 5, 6)
    println(b)//ArrayBuffer(3, 4, 5, 6)

    //可以使用 ++= 操作符在尾部追加任何集合
    b ++= Array(8, 9 ,10, 12)
    println(b)//ArrayBuffer(3, 4, 5, 6, 8, 9, 10, 12)

    //移除最后5个元素
    //在数组缓冲的尾端添加或移除元素是一个高效
    // ("amortized constant time"，平摊常量时间)的操作
    b.trimEnd(5)
    println(b)//ArrayBuffer(3, 4, 5)

    println("==========================insert==========")
    //任意位置插入或移除元素，
    // 但这样的操作并不那么高效--所有在那个位置之后的元素都必须被平移
    //在2 下标之前插入 6
    b.insert(2, 6)
    println(b)//ArrayBuffer(3, 4, 6, 5)

    //在2 下标之前插入 7, 8, 9
    b.insert(2, 7, 8, 9)
    println(b)//ArrayBuffer(3, 4, 7, 8, 9, 6, 5)

    println("==========================remove==========")

    //移除下标2 的元素
    b.remove(2)
    println(b)//ArrayBuffer(3, 4, 8, 9, 6, 5)

    //移除下标2 后 2个元素
    b.remove(2, 3)
    println(b)//ArrayBuffer(3, 4, 5)

    val c = b.toArray
    b.insert(2, 10)
    //c.insert(2, 10) //Array 定长

    val d = c.toBuffer
    d.insert(2, 11)
  }

  /**
    * 遍历数组
    */
  @Test
  def arrayLoop(): Unit ={
    val arr = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    for(i <- 0 until arr.length)
      println(s"$i : ${arr(i)}")
    //until 开包； to 闭包

    println("====================每2个元素一跳==============")
    //每2个元素一跳
    for(i <- 0 until arr.length by 2)
      println(s"$i: ${arr(i)}")

    println("====================从数组尾端开始=============")
    //从数组尾端开始
    for(i <- arr.length-1 to 0 by -1)
      println(s"$i: ${arr(i)}")

    println("====================indices=============")
    for(i <- arr.indices)
      println(s"$i: ${arr(i)}")

    println("====================indices.reverse=============")
    for(i <- arr.indices.reverse)
      println(s"$i: ${arr(i)}")

    println("====================无下标=============")
    for(a <- arr)
      println(s"$a")
  }

  /**
    * 数组转换
    */
  @Test
  def arrayTransform(): Unit ={
    val arr = Array(2, 3, 4, 5, 6, 7, 11)

    println("===============for推导式 yield===============")
    val result = for(a <- arr) yield a * 2
    result.foreach(println(_))

    println("===============守卫===============")
    val result2 = for(a <- arr if a % 2 == 0) yield a * 2
    result2.foreach(println(_))

    println("===============arr.filter.map===============")
    val result3 = arr.filter(_ % 2 == 0).map(_ * 2)
    result3.foreach(println(_))

    println("===============arr filter map===============")
    val result4 = arr filter {_ % 2 == 0} map {_ * 2}
    result4.foreach(println(_))
  }

  /**
    * 高效删除元素
    */
  @Test
  def removeElem(): Unit ={
    //删除数组中的负数
    val arr = ArrayBuffer(1, 3, 7, -5, -8, 11, -15, 12, 16)
//    val positionsToRemove = for(i <- arr.indices if arr(i) < 0) yield i
//    for(i <- positionsToRemove.reverse) arr.remove(i)

    val positionsToKeep = for(i <- arr.indices if arr(i) > 0) yield i
    for(i <- positionsToKeep.indices) arr(i) = arr(positionsToKeep(i))
    arr.trimEnd(arr.length - positionsToKeep.length)
    arr.foreach(println(_))

    //要点：拿到所有下标好过逐个处理
  }

  /**
    * 常用算法
    */
  @Test
  def testAlgs(): Unit ={
    println("===============sum===============")
    println(Array(1, 7, 2, 9).sum)
    println("===============max===============")
    println(ArrayBuffer("Mary", "had", "a", "little", "littla","littlaa","lamb").max)

    println("===============sorted===============")
    val b = Array(1, 7, 2, 9)
    val bSorted = b.sorted
    bSorted.foreach(println(_))

    println("===============descending===============")
    val bDescending = b.sortWith(_ > _)
    bDescending.foreach(println(_))

    println("===============quickSort===============")
    //可以直接对一个数组排序，但不能对数组缓冲排序
    val a = Array(1, 7, 2, 9)
    scala.util.Sorting.quickSort(a)
    a.foreach(println(_))
    println(a.mkString(","))
    println(a.mkString("<", ",", ">"))
    println(a.toString)

    val c = ArrayBuffer(1, 7, 2, 9)
    println(c.toString())
  }

  /**
    * 与java互操作
    */
  @Test
  def correlateJava(): Unit ={
    val a = Array("Mary", "a", "had", "lamb", "little")
    //这样行不通
    //因为Scala不会将Array[String]转换为Array[Object]
    //java.util.Arrays.binarySearch(a, "beef")
    //可以像这样强制转换
    var position =java.util.Arrays.binarySearch(a.asInstanceOf[Array[Object]], "a")
    println(position)

    //直接用Scala二分查找
    import scala.collection.Searching._
    val result = a.search("beef")
    println(result)
    println(a.search("a"))

    //如果你调用接受或返回java.util.List的java方法，当然可以在Scala代码中使用java的ArrayList
    //但那样做没什么意思。你完全可以引入scala.collection.JavaConversions里的隐式转换方法。
    //这样你就可以在代码中使用scala缓冲，在调用Java方法是，这些对象会被自动包装成java列表
    //举例来说，java.util.ProcessBuilder类有一个以List<String>为参数的构造函数。
    //以下是在Scala中调用它的写法：
    import scala.collection.JavaConversions.bufferAsJavaList
    import scala.collection.mutable.ArrayBuffer
    val command = ArrayBuffer("ls", "-al", "/home/cay")
    val pb = new ProcessBuilder(command)//Scala到Java的转换,Scala缓冲被包装成了一个实现了java.util.List接口的Java类的对象


    //反过来讲，当java方法返回java.util.List时，我们可以让它自动转换成一个Buffer:
    import scala.collection.JavaConversions.asScalaBuffer
    import scala.collection.mutable.Buffer
    val cmd: Buffer[String] = pb.command()//Java到Scala的转换
    //不能使用ArrayBuffer--包装起来的对象仅能保证是一个Buffer
  }


  //================================练 习==========================

  /**
    * 将a设置为一个n个随机整数的数组，要求随机数介于0（包含）和n（不包含）之间
    * @param n
    */
  def exercises1(n : Int): Unit ={
    val a = new Array[Int](n)
    for(i <- 0 until n)
      a(i) = (new util.Random).nextInt(n)
    println(a.mkString(","))
  }

  @Test
  def testExercises1(): Unit ={
    exercises1(1)
  }

  /**
    * 编写一个循环，将整数数组中相邻的元素置换
    */
  @Test
  def exercises2(): Unit ={
    val a = Array(1, 2, 3, 4, 5, 6, 7)
    val b = new Array[Int](a.size)
    var temp : Int = 0
    for(i <- 1 until a.size by 2){
      b(i-1) = a(i)
      b(i) = a(i-1)
      println(i)
      if(a.size % 2 == 1 && i==a.size-2)
        b(a.size-1)=a(a.size-1)
    }
    println(b.mkString(","))
  }

  def swapArray(a: Array[Int]) ={
    for(i <- 0 until a.size)
      yield
        if(i%2==0)
          if(i == a.size - 1)
            a(i)
          else
            a(i+1)
        else
          a(i-1)
  }

  @Test
  def testSwapArray(): Unit ={
    val a = Array(1, 2, 3, 4, 5, 6, 7)
    println(swapArray(a).mkString(","))
  }


  @Test
  def exercises4(): Unit ={
    val arr = Array(1, -1, 2, 3, -5, 4, 8, -10, -9)
    var b = new ArrayBuffer[Int]()
    var c = new ArrayBuffer[Int]()
    for(a <- arr){
      if(a > 0)
        b.append(a)
      else
        c.append(a)
    }
    b.appendAll(c)
    println(b.mkString(","))
  }

  def positiveNegative(a: Array[Int])={
    a.filter(_ > 0) ++ a.filter(_ < 0)
  }

  @Test
  def testPositiveNegative(): Unit ={
    val test = Array(10, 7, -5, 11, -44, 0, 22, -22, -100, 77, -5)
    val res = positiveNegative(test)
    println(res.mkString(","))
  }

  def calArrayAvg(a: Array[Double]): Double={
    a.sum/a.size
  }

  @Test
  def testCalArrayAvg(): Unit ={
    val test = Array[Double](10, 7, -5, 11, -44, 0, 22, -22, -100, 77, -5)
    val res = calArrayAvg(test)
    println(res)
  }

  @Test
  def reverse(): Unit ={
    val test = Array(10, 7, -5, 11, -44, 0, 22, -22, -100, 77, -5)
    println(test.reverse.mkString(","))

    val test2 = ArrayBuffer(10, 7, -5, 11, -44, 0, 22, -22, -100, 77, -5)
    println(test2.reverse.mkString(","))
  }


}
