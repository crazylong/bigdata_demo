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
}
