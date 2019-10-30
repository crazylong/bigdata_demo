package impatient.chapter10

/**
 * 叠加在一起的特质
 */
trait Logger5 {
  def log(msg: String)
}

trait ConsoleLogger5 extends Logger5 {
  def log(msg:String){println(msg)}
}

trait TimestampLogger5 extends ConsoleLogger5{
  override def log(msg: String): Unit ={
    super.log(s"${java.time.Instant.now()} $msg")
  }
}

trait ShortLogger5 extends ConsoleLogger5{
  override def log(msg: String) {
    super.log(
      if (msg.length <= 15) msg else s"${msg.substring(0, 12)}...")
  }
}

abstract class SavingsAccount5 extends Account with Logger5 {
  def withdraw(amount: Double) {
    if (amount > balance) log("Insufficient funds")
    else balance -= amount
  }

  // More methods ...
}

object Main5 extends App{
  val acct1 = new SavingsAccount5 with TimestampLogger5 with ShortLogger5
  val acct2 = new SavingsAccount5 with ShortLogger5 with TimestampLogger5
  acct1.withdraw(100)
  acct2.withdraw(100)
}