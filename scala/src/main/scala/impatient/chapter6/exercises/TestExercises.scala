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

  @Test
  def testPlayingCardEnum(): Unit ={
    println(PlayingCardEnum.values)
  }

  @Test
  def testCheckCard(): Unit ={
    val card = new Card(1, PlayingCardEnum.RED)
    if(card.playingCardEnum == PlayingCardEnum.RED)
      println("是红桃")
    else
      println("不是红桃")
}
}
