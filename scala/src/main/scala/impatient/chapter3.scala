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


}
