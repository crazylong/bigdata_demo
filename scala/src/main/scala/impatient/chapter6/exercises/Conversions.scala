package impatient.chapter6.exercises

object Conversions extends UnitConversion {
  def inchesToCentimeters(inches: Long):Long={
    inches * 10
  }

  def gallonsToLiters(gallon:Long):Long={
    gallon * 100
  }

  def milesToKilometers(mile:Long):Long={
    mile * 1000
  }
}
