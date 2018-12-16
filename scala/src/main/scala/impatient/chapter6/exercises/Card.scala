package impatient.chapter6.exercises

import impatient.chapter6.exercises.PlayingCardEnum.PlayingCardEnum


class Card( var id : Int, var playingCardEnum : PlayingCardEnum) {
}

object Card{
  def apply(id: Int, playingCardEnum: PlayingCardEnum): Unit ={
    return new Card(id, playingCardEnum)
  }
}
