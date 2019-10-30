package impatient.chapter10

/**
 * 自身类型
 * 当特质扩展类时，编译器能够确认的是，所有混入该特质的类都将这个类作为超类，
 * Scala另一套机制也可以保证这点--自身类型(self type)
 * this: 类型 =>
 */
trait Logger13 {
  def log(msg: String)
}

trait ConsoleLogger13 extends Logger13 {
  def log(msg: String){ println(msg)}
}

trait LoggedException13 extends ConsoleLogger13 {
  //只能被混入Exception及其子类
  this: Exception =>
    def log() {log(getMessage)}
  //只能被混入NumberFormatException及其子类
//  this: NumberFormatException =>
//    def log() {log(getMessage)}
}

class SavingAccount13 extends Account {
  def withdraw(amount: Double): Unit ={
    if(amount > balance) throw
      new IllegalStateException("Insufficient funds")
        with LoggedException13 with ConsoleLogger13
    else balance -= amount
  }
}

object Main13 extends App {
  try{
    val acct = new SavingAccount13
    acct.withdraw(100)
  } catch {
    case e: LoggedException13 => e.log()
  }
}
