package impatient.chapter5

import scala.collection.mutable.ArrayBuffer

/**
  * 嵌套类
  * 在Scala中，你几乎可以在任何语法结构中内嵌任何语法结构，
  * 你可以在函数中定义函数，在类中定义类
 *
  * @author Long Qiong 
  * @create 2018/12/2
  */
class Network {
  class Member(val name: String){
    val contacts = new ArrayBuffer[Member]

    //类型投影(type projection) Network#Member，其含义是“任何Network的Member”
    val contacts2 = new ArrayBuffer[Network#Member]
  }

  private val members = new ArrayBuffer[Member]

  def join(name: String)= {
    val m = new Member(name)
    members += m
    m
  }
}
