package impatient.chapter10

/**
 * 带有特质的对象
 */
trait Logger4 {
  def log(msg: String)
}

abstract class SavingsAccount4 extends Account with Logger4{
  //这个类是抽象的，它还不能做任何日志输出的动作，
  //不过可以在构造对象时“混入”一个具体的日志记录器实现
  def withDraw(amount: Double): Unit ={
    if(amount > balance) log("Insufficient funds")
    else balance -= amount
  }
}

trait ConsoleLogger4 extends Logger4 {
  def log(msg:String){println(msg)}
}

object Main4 extends App{
  //在构造对象时“混入”一个具体的日志记录器实现
  val acct = new SavingsAccount4 with ConsoleLogger4
  acct.withDraw(100)
}


