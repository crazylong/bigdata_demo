package impatient.chapter6.exercises

object PlayingCardEnum extends Enumeration {
  type PlayingCardEnum = Value
  var BLACK = Value("♠")
  val RED = Value("♥")
  val CLUB=Value("♣")
  val DIAMOND=Value("♦")

  def isRed(c : PlayingCardEnum):Boolean={
    c==RED || c==DIAMOND
  }

}
