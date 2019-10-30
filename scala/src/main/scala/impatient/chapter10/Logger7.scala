package impatient.chapter10

/**
 * 当作富接口使用的特质
 */
trait Logger7 {
  def log(msg: String)
  def info(msg: String){log(s"INFO:$msg")}
  def warn(msg: String){log(s"WARN:$msg")}
  def severe(msg: String){log(s"SEVERE:$msg")}
}

trait ConsoleLogger7 extends Logger7{
  override def log(msg: String): Unit = println(msg)
}


abstract class SavingAccount7 extends Account with Logger7 {
  def withdraw(amount: Double): Unit ={
    if(amount > balance) severe("Insufficient funds")
    else balance -= amount
  }
}

object Main7 extends App{
  val sa = new SavingAccount7 with ConsoleLogger7
  sa.withdraw(100)
}