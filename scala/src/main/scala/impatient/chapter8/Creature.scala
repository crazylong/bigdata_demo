package impatient.chapter8

/**
 * 构造顺序和提前定义
 * -Xcheckinit
 */
class Creature {
  val range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}

class Ant extends Creature{
  override val range = 2
}
