package impatient.chapter6.exercises

/**
  *
  * @author Long Qiong 
  * @create 2018/12/7
  */
class Point {
  var x : Integer = 0
  var y : Integer = 0


  override def toString = s"Point(x=$x, y=$y)"
}

object Point{
  def apply(x: Integer, y: Integer): Point ={
    val p : Point = new Point
    p.x = x
    p.y = y
    p
  }
}

