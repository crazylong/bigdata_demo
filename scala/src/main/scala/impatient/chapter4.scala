package impatient

import java.io.File
import java.util.Scanner

import org.junit.Test

import scala.collection.immutable.TreeMap
import scala.collection.{SortedMap, mutable}
import scala.util.Random

class chapter4 {
  /**
    * Scala中，映射时对偶的集合
    */
  def mapDefine(): Unit ={
    //构造不可变Map[String, Int]
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5)
    val scores1 = Map(("Alice", 10), ("Bob",3), ("Cindy", 5))

    val bobsScorces = scores("Bob")//类似于Java scorces.get("Bob")，所以scala中函数和映射之间的相似性更加明显

    //如果映射中不包含键，会抛出异常，可用contains检验
    val bobsScorces2 = if (scores.contains("Bob")) scores("Bob") else 0
    val bobsScores3 = scores.getOrElse("Bob", 0)


    //可变映射
    val scores2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5)

    //空映射
    val scorces3 = scala.collection.mutable.Map[String, Int]()
  }

  @Test
  def testMapGet(): Unit ={
    //拿到一个不可变映射，你可以通过给出对不存在的键的固定默认值，
    //或计算默认值的函数，将它转换成一个映射
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5)
    val scores1 = scores.withDefaultValue(0)

    //将交出0，因为"Zelda"不存在（实际运行结果为None)
    val zeldasScores1 = scores1.get("Zelda")
    println(zeldasScores1)

    //将交出5，因为"Zelda"的长度为5（实际运行结果为None)
    val scores2 = scores.withDefault(_.length)
    val zeldasScores2 = scores2.get("Zelda")
    println(zeldasScores2)

    val bobScores = scores.get("Bob")
    println(bobScores)
  }

  @Test
  def testMapUpdate(): Unit ={
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5)
    val scores2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5)

    //不可变集合不能修改值
    //    scores("Bob") = 10
    //    println(scores("Bob"))
    scores2("Bob") = 10
    println(scores2("Bob"))

    scores2("Fred") = 7
    println(scores2("Fred"))

    scores2+=("Lily" -> 10, "Dove" -> 20)
    scores2 -= "Bob"

    //newScores映射包含了与scores相同的映射关系；此外，"bob"被更新，"Fred"被添加进来
    val newScores = scores + ("Bob" -> 10, "Dove" -> 20)

    //除了把结果作为新值保存，还可以更新var变量:
    var scores3 = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5)
    scores3 = scores3 + ("Bob" -> 10, "Dove" -> 20)
    scores3 += ("Lily" -> 10)
    println(scores3("Lily"))
    println(scores3)
    scores3 -= "Lily"
    //var 可以追加元素，但不能直接修改
//    scores3("Lily") = 30
//    println(scores3("Lily"))

    //你可能会觉得这样不停地创建新映射效率很低
  }

  /**
    * 元组
    * 映射是键/值对偶的集合。对偶是元组的最简单形态--元组是不同类型的值的聚集
    * 元组的值是通过将单个的值包含在圆括号中构成的
    */
  @Test
  def testTuple(): Unit ={
    val t1 = (1, 3.14, "Fred")

    /**
      * Error:(90, 44) object java.lang.String is not a value
      * val t2 = Tuple3(Int, Double, java.lang.String)
      */
    //val t2 = Tuple3(Int, Double, java.lang.String)

    /**
      * Error:(91, 20) missing argument list for method apply in object Tuple3
      * Unapplied methods are only converted to functions when a function type is expected.
      * You can make this conversion explicit by writing `apply _` or `apply(_,_,_)` instead of `apply`.
      * val t3 = Tuple3[Int, Double, java.lang.String]
      */
    //val t3 = Tuple3[Int, Double, java.lang.String]

    //val t4 = Tuple3.apply[Int, Double, java.lang.String]
    val e1 = t1._1
    //val e2 = t2 _2
    //val e3 = t3.apply(1, 2.1, "abc")
    //val e4 = t4.apply(1, 2.1, "abc")
    println(t1)
    //println(t2)
    //println(t3)
    println(e1)
    //println(e2)
    //println(e3)

   // println(t4)
    //println(e4)

    //通常使用模式匹配来获取元组的组成部件
    val (first, second, third) = t1
    println(second)

    //如果并不是所有的部件都需要，那么可以在不需要的部件位置上使用_
    val (a, b, _) = t1

    //元组可以用于函数需要返回不止一个值的情况。举例来说，StringOps的partition方法返回的是一对字符串，
    // 分别包含了满足某个条件和不满足该条件的字符：
    val par = "New York".partition(_.isUpper)
    println(par)

  }

  /**
    * 拉链操作
    * 使用元组的原因之一是把多个值绑在一起，以便它们能被一起处理，这通常可以使用zip方法来完成
    */
  @Test
  def testZip(): Unit ={
    val symbols = Array("<", "-", ">")
    val counts = Array(2, 10, 2)

    //将交出对偶组成的数组 Array(("<", 2), ("-", 10), (">", 2))
    val pairs = symbols.zip(counts)
    println(pairs)

    println("="*100)

    //然后，这些对偶就可以被一起处理了
    for((s, n) <- pairs) print(s * n)
    println("="*100)

    //用toMap 的方法可以将对偶的集合转换成映射
    val mapPairs = pairs.toMap
    println(mapPairs)

    //如果你有一个键的集合，以及一个与之平行对应的值的集合，
    // 就可以使用拉链操作将它们组合成一个映射
    //keys.zip(values).toMap
  }

  //=====================================练习========================

  def prn(x: TraversableOnce[_]) = println(x.mkString(x.getClass.getSimpleName + "(", ", ", ")"))

  @Test
  def testExercises1(): Unit ={
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5, "Lucy" -> 9)
    val scores2 = for((k, v) <- scores) yield (k, v*0.9)
    prn(scores)
    println(scores2)

    val scores3 = scores.map(m => (m._1, m._2*0.9))
  }

  @Test
  def testExercises2(): Unit ={
    val in = new Scanner(new File("E:\\books\\大数据\\bigdata_demo\\scala\\src\\main\\resources\\myFile.txt"))
    val m = scala.collection.mutable.Map.empty[String, Int]
    while (in.hasNext()){

      val ci = in.next()
      if(m.contains(ci)) m(ci) += 1 else m(ci) = 1
    }

    println(m)
  }

  @Test
  def testExercises2_2(): Unit ={
    import scala.io.Source
    val source = Source.fromFile("E:\\books\\大数据\\bigdata_demo\\scala\\src\\main\\resources\\myFile.txt", "UTF-8")
    val tokens = source.mkString.split("\\s+")
    val freq = new mutable.HashMap[String, Int]()
    tokens foreach{token =>
      if(freq.contains(token))
        freq(token) += 1
      else
        freq(token) = 1
    }

    prn(freq)
  }

  @Test
  def testExercises3(): Unit ={
    val in = new Scanner(new File("E:\\books\\大数据\\bigdata_demo\\scala\\src\\main\\resources\\myFile.txt"))
    var m = new scala.collection.immutable.HashMap[String, Int]
    while (in.hasNext()){

      val ci = in.next()

      m += (ci -> (m.getOrElse(ci, 0) + 1))
    }

    println(m)
  }

  @Test
  def testExercises4(): Unit ={
    val in = new Scanner(new File("E:\\books\\大数据\\bigdata_demo\\scala\\src\\main\\resources\\myFile.txt"))
    var m : SortedMap[String, Int] = new TreeMap[String, Int]
    while (in.hasNext()){

      val ci = in.next()

      m += (ci -> (m.getOrElse(ci, 0) + 1))
    }

    println(m)
  }

  @Test
  def testExercises5(): Unit ={
    val in = new Scanner(new File("E:\\books\\大数据\\bigdata_demo\\scala\\src\\main\\resources\\myFile.txt"))
    val m = new java.util.TreeMap[String, Int]
    while (in.hasNext()){

      val ci = in.next()

      if(m.containsKey(ci))
        m.put(ci,  m.get(ci) + 1)
      else
        m.put(ci, 1)
    }

    import scala.collection.JavaConversions.mapAsScalaMap
    val scalaM = m

    println(m)
    prn(scalaM)
  }

  @Test
  def testExercises6(): Unit ={
    val m = new mutable.LinkedHashMap[String, Int]
    m += ("Monday" -> java.util.Calendar.MONDAY, "SUNDAY" -> java.util.Calendar.SUNDAY)
    prn(m)
  }

  @Test
  def testExercises7(): Unit ={
    import scala.collection.JavaConversions.propertiesAsScalaMap
    val propMap = propertiesAsScalaMap(System.getProperties)

    val maxKey = propMap.keys.map(_.length).max



    propMap.foreach(p => {
      printf("%-" + maxKey + "s | %s\n",  p._1, p._2)
    })
  }

  def minMax(values : Array[Int]): (Int, Int) ={
    val min = values.min
    val max = values.max
    (min -> max)
  }

  @Test
  def testExercises8(): Unit ={
    val arr = Array(5, 3, 7, 1)
    println(minMax(arr))
  }

  def lteqgt(values:Array[Int], v: Int) ={
    val lt = values.count(_ < v)
    val eq = values.count(_ == v)
    val gt = values.count(_ > v)
    (lt, eq, gt)
  }

  def randArray(n : Int) = {
    val a = new Array[Int](n)
    for(i <- 0 until a.size) a(i) = Random.nextInt(n)
    a
  }

  def lteqgt_2(values:Array[Int], v: Int) =(
    values.count(_ < v),
    values.count(_ == v),
    values.count(_ > v)
  )

  @Test
  def testExercises9(): Unit ={
    val arr = Array(5, 3, 7, 1, 2, 4, 6, 8,5, 5)
    println(lteqgt(arr, 5))

    val arr2 = randArray(10)
    println(lteqgt_2(arr2, 5))
  }

  @Test
  def testExercises10(): Unit ={
    println("Hello".zip("World"))
  }

  @Test
  def testExercises10_2(): Unit ={
    val caseConverter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".zip("abcdefghijklmnopqrstuvwxyz").toMap

    var lower = caseConverter('D')

    println(lower)
  }


}
