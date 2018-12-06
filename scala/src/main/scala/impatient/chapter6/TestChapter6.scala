package impatient.chapter6

import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/12/3
  */
class TestChapter6 {
  @Test
  def testApply(): Unit ={
    val acct = Account(1000.0)
  }

  @Test
  def testEnum(): Unit ={
    println(doWhat(TrafficLightColor.Red))

    for(c <- TrafficLightColor.values)
      println(s"${c.id}: $c")

    //通过枚举的ID或名称来进行查找定位
    println(TrafficLightColor(0)) //将调用Enumeration.apply
    //println(TrafficLightColor.withName("Red"))
    println(TrafficLightColor.withName("Stop"))
  }
  import TrafficLightColor._
  //如果没有使用类型别名(type TrafficLightColor = Value)下面方法定义只能使用：
  //def doWhat(color: TrafficLightColor.Value)={ }
  def doWhat(color: TrafficLightColor)={
    if(color == Red) "Stop"
    else if(color == Yellow) "hurry up"
    else "go"
  }
}
