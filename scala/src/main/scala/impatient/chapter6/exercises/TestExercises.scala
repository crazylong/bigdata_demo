package impatient.chapter6.exercises

import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/12/7
  */
class TestExercises {
  @Test
  def testUnitConversion(): Unit ={
    println(InchesToCentimeters.conversion(10))
  }

  @Test
  def testOrigin(): Unit ={
    println(Origin)
  }

  @Test
  def testPoint(): Unit ={
    println(Point(2, 3))
  }

}
