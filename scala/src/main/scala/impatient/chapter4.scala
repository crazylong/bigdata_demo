package impatient

import org.junit.Test

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

    //你可能会觉得这样不停地创建新映射效率很低，不过事实并非如此。
    //老的和新的映射共享大部分结构。（这样做之所以可行，是因为它们是不可变的。）
  }

  @Test
  def testIterable(): Unit ={
    val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 5, "Lily" -> 5)
    for((k,v) <- scores)
      println(k + ":" + v)

    for(k <- scores.keySet)
      println(k)

    for(k <- scores.keys)
      println(k)

    for(v <- scores.values)
      println(v)

    val newMap =for((k, v) <- scores)
      yield (v, k)
    println(newMap)
  }

  /**
    * 映射有两种常见的实现策略：哈希表和平衡树。
    * 哈希表使用键的哈希码来划定位置，因此遍历会以一种不可预期的顺序交出元素。
    * 默认而言，Scala给你的是基于哈希表的映射，因为它通常更高效。
    * 如果需要按照顺序依次访问映射中的键，可以使用SortedMap
    */
  @Test
  def testSortedMap(): Unit ={
    //哈希码排序
    val scores1 = scala.collection.mutable.Map("Alice" -> 10, "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)
    println(scores1)

    //根据键排序
    val scores2 = scala.collection.mutable.SortedMap("Alice" -> 10, "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)
    println(scores2)

    //根据插入顺序排序
    val scores3 = scala.collection.mutable.LinkedHashMap("Alice" -> 10, "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)
    println(scores3)
  }

  /**
    * 与java互操作
    */
  @Test
  def correlateJava(): Unit ={
    //java to scala
    var javaMap = new java.util.TreeMap[String, Int]()
    javaMap.put("Alice", 10)
    javaMap.put("Fred", 7)
    javaMap.put("Bob", 3)
    javaMap.put("Cindy", 8)
    println(javaMap)

    import scala.collection.JavaConversions.mapAsScalaMap
    val scalaMap : scala.collection.mutable.Map[String, Int] = javaMap
    println(scalaMap)

    import scala.collection.JavaConversions.propertiesAsScalaMap
    val props: scala.collection.Map[String, String] = System.getProperties
    //println(props)


    //scala to java
    import scala.collection.JavaConversions.mapAsJavaMap
    import java.awt.font.TextAttribute._
    val attrs = Map(FAMILY -> "Serif", SIZE -> 12) //Scala映射
    val font = new java.awt.Font(attrs) //scala to java
    println(attrs)
    println(font)
  }
}
