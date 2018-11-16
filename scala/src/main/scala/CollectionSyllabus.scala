import org.junit.Test

import scala.collection.mutable.ArrayBuffer

/**
  * 集合
  * @author Long Qiong 
  * @create 2018/10/7
  */
class CollectionSyllabus {
  @Test
  def test1(): Unit ={
    val array = new Array[Int](10)
    println(array)
    println(array.mkString(","))
    array(1)=7
    println(array(1))
  }

  //定长/变长
  @Test
  def test2(): Unit ={
    //定长数组
    val arr0 = new Array[Int](10)
    val arr1= Array(1, 2)
    println(arr1.mkString(","))
    arr1.toBuffer
    //arr1 += 100
    //定长转变长
    val arr3 = arr1.toBuffer
    arr3+=100

    //变长数组
    val arr2 = ArrayBuffer[Int]()
    arr2.append(7)
    println(arr2.mkString(","))
    arr2 += 10
    println(arr2.mkString(","))
    arr2.append(1,2,3,4)
    println(arr2.mkString(","))

    //变长转定长
    var arr4 = arr2.toArray

  }

  //多维数组
  @Test
  def test3(): Unit ={
    val arr1 = Array.ofDim[Double](3,4)
    arr1(0)(0)=11.11
    println(arr1.mkString(","))
    println(arr1.mkString(",").mkString(","))
    arr1.foreach(x=>println(x.mkString(",")))
  }

  //Scala和Java数组的互相转换
  @Test
  def test4(): Unit ={
    val arr1 = ArrayBuffer("1", "2", "3")
    //Scala to Java
    import scala.collection.JavaConversions.bufferAsJavaList
    val javaArr = new ProcessBuilder(arr1)
    println(javaArr.command())
    println(javaArr.command().getClass)

    //Java to Scala
    import scala.collection.JavaConversions.asScalaBuffer
    import scala.collection.mutable.Buffer
    val scalaArr:Buffer[String] = javaArr.command()//隐式转换
    println(scalaArr)
    println(scalaArr.mkString(","))


  }

  //集合遍历
  @Test
  def test5(): Unit ={
    val arr1 = ArrayBuffer("1", "2", "3")
    for(i <- arr1){
      println(i)
    }

    println()

    //集合中的元素只使用一次可以用下划线代替
    arr1.foreach(println(_))
    println()
    arr1.foreach(i=>println(i))
  }

  //元组
  @Test
  def test6(): Unit ={
    //val tuple1 = Tuple1(10, 20, 30)// ---> val tuple1 = Tuple1(Tuple3(10, 20, 30))
    val tuple1 = Tuple1(10)
    println(tuple1)
    //val tuple2 = Tuple2(10, 20, 30)
    val tuple2 = Tuple2(10, 20)
    println(tuple2)
    val tuple3 = Tuple1(10, 20, 30)// ---> val tuple3 = Tuple1(Tuple3(10, 20, 30))
    println(tuple3)

    println(tuple2 _1)
    println(tuple2._2)

    println(tuple3._1)
    println(tuple3._1 _1)
  }

  //元组遍历
  @Test
  def test7(): Unit ={
    val tuple5 = Tuple5(10, 20, 30, 40, 50)
    //元组遍历必需生成迭代器
    for(t <- tuple5.productIterator){
      println(t)
    }

    println()
    tuple5.productIterator.foreach(println(_))
  }

  //列表，默认不可变集合
  @Test
  def test8(): Unit ={
    val list1 = List(1,2)
    println(list1)

    for(i <- list1){
      println(i)
    }
    println()
    println(list1(1))

    //追加元素
    val list2 = list1 :+ 20
    println(list1)
    println(list2)

    //可变集合
    var list3 = collection.mutable.ListBuffer(1, 2)
    println(list3)
    list3.append(3, 4)
    list3 += 5
    println(list3)

    val list4 = 100 +: list3
    println(list4)

    val list5 = 1 :: 2 :: 3 :: Nil //Nil表示List()
    println(list5)

    val list6 = 1 :: 2 :: 3 :: List() //Nil表示List()
    println(list6)

    val list7 = List(1,2,3) :: List(4,5,6)
    println(list7)

    val list8 = List(1,2,3) :: List(4,5,6) :: Nil
    println(list8)

    val list9 = List(1,2,3) ::: List(4,5,6) ::: List(4,5,6)
    println(list9)
  }

  //Map
  @Test
  def test9(): Unit ={
    val map1 = Map("Alice" -> 10, "Bob" -> 20, "Kotlin" -> 30)
    val map2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 20, "Kotlin" -> 30)
    //空的映射(必须是可变的，不可变的空映射没有意义)
    val map3 = new scala.collection.mutable.HashMap[String, Int]

    //对偶元组
    val map4 = Map(("Alice", 10), ("Bob", 20), ("Kotlin", 30))
    println(map1)
    println(map4)

    val value1 = map1("Alice")
    println(value1)
    //map1("Alice") = 99

    map2("Alice") = 99
    println(map2("Alice"))
    map2 += ("Bob" -> 99)
    println(map2)

    map2 -=("Alice", "Bob")
    println(map2)

    map2 += ("Bob" -> 99)
    println(map2)

    val map5 = map2 + ("AAAA" -> 40, "BBBB" -> 50)
    println(map5)
  }

  @Test
  def test10(): Unit ={
    val map1 = Map("Alice" -> 10, "Bob" -> 20, "Kotlin" -> 30)
    for(i <- map1){
      println(i)
    }
    println()
    for(i <- map1.keys){
      println(i)
    }
    println()
    for(i <- map1.values){
      println(i)
    }
    println()
    for(i <- map1){
      println(i._1 + ":" + i._2)
    }

    println()
    for((k,v) <- map1){
      println(k + ":" + v)
    }
  }

  //集合中的元素，映射某个函数
  @Test
  def test11(): Unit ={
    val names = List("Alice", "Bob", "Nick")
    println(names.map(_.toUpperCase))
  }

  //拉链
  @Test
  def test12(): Unit ={
    val list1= List("Alice", "Bob", "Nick")
    val list2 = List("20", "22")
    val list3 = list1 zip list2
    println(list3)
  }

  //使用#::得到一个stream
  @Test
  def test13(): Unit ={
    var numStream = numsForm(5)
    println(numStream)
    println(numStream.tail)
    println(numsForm(5).tail)
    println(numStream)
  }

  def numsForm(n:BigInt):Stream[BigInt] = n #:: numsForm(n+1)

  //视图
  //我们找到10万以内，所有数字倒序排列还是它本身的数字
  @Test
  def test14(): Unit ={
    val view =(1L to 10000000L).view.map(x => x).filter(y => y.toString == y.toString.reverse)
    println (view.mkString(" "))
//    for(x <- view){
//      print(x + "，")
//    }
  }
}
