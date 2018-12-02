package impatient.chapter5.exercises

import org.junit.Test

/**
  * 不要在Int.MaxValue时变成负数
  *
  * @author Long Qiong 
  * @create 2018/12/1
  */
class Counter {
  private var value = 0

  def increment() {
    if(value != Int.MaxValue)
      value += 1
  }

  def current() = value

  @Test
  def test1(): Unit ={
    value = Int.MaxValue
    increment()
    println(current)
  }
}
