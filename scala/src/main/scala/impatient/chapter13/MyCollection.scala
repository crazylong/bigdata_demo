package impatient.chapter13

import org.junit.Test

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class MyCollection {

  @Test
  def testList(): Unit ={
    val digits = List(4, 2)

    val d2 = 9 :: digits

    val d3 = 9 :: 4 :: 2 :: Nil

    var d4 = 9 :: (4 :: (2 :: Nil))

    println(d4.sum)

    println(d4)
    println(sum(d4))

    d4 = d4 :+ 5
    println(d4)
    d4 = 6 +: d4
    println(d4)

    //错误方式
    //d4 = d4 :+ List(7, 8, 9)
    //d4 = d4 :+ List[Int](7, 8, 9)
    //println(d4)
    //d4 = d4 - 9
  }

  @Test
  def testListBuffer(): Unit ={
    val list1 : ListBuffer[Int] = ListBuffer(1, 2, 3)
    //list + 1
    list1 += 4
    println(list1)

    list1 -= 3
    println(list1)

    val list2 : ListBuffer[Map[String, Int]] = ListBuffer(Map(("a"-> 1), ("b"-> 2)))

    println(list2)
    list2 += Map(("c", 3))
    println(list2)
  }

  @Test
  def testSet(): Unit ={
    var set = Set(2, 0, 1) +1

    set += 4
    set += (5, 6, 7)
    println(set)

    set -= 2
    println(set)

    val set2 = mutable.LinkedHashSet("a", "b", "c")
    println(set2)

  }


  def sum(lst : List[Int]) : Int = if(lst == Nil) 0 else lst.head + sum(lst.tail)

  @Test
  def testListMap(): Unit ={
    var list : mutable.ListMap[String, String] = mutable.ListMap(("a", "a"), ("b", "b"))

    println(list)
  }

}
