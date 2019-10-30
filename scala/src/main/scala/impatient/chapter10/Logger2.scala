package impatient.chapter10

/**
 * 带有具体实现的特质
 */
trait Logger2 {
}

trait ConsoleLogger2{
  def log(msg:String){println(msg)}
}

class SavingsAccount2 extends Account with ConsoleLogger2{
  //ConsoleLogger2的功能被“混入”了SavingsAccount2类
  def withDraw(amount: Double): Unit ={
    if(amount > balance) log("Insufficient funds")
    else balance -= amount
  }
}

object Main2 extends App{
  val acct = new SavingsAccount2
  acct.withDraw(100)
}


