package impatient.chapter6.exercises

class UnitConversion(factor:Double) {
  def conversion(value: Double)={
    value * factor
  }
}

object InchesToCentimeters extends UnitConversion(2.54)

object GallonsToLiters extends UnitConversion(3.78541178)

object MilesToKilometers extends UnitConversion(1.609344)
