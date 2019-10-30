package impatient.chapter10

/**
 * 特质中的抽象字段
 * 特质中未被初始化的字段在具体的子类中必须被重写
 */
trait Logger9 {
 def log(msg: String)
}

trait ConsoleLogger9 extends Logger9{
  override def log(msg: String): Unit = {
    println(msg)
  }
}

trait ShortLogger9 extends Logger9{
  val maxLength: Int
  abstract override def log(msg: String): Unit = {
    super.log(
      if(msg.length>maxLength) s"${msg.substring(0, maxLength-3)}..."
      else msg)
  }
}

abstract class SavingAccount9 extends Account with Logger9 {
  var interest = 0
  def withdraw(amount: Double): Unit ={
    if(amount>balance) log("Insufficient funds")
    else balance -= amount
  }
}

object Main9 extends App{
  val acct = new SavingAccount9 with ConsoleLogger9 with ShortLogger9{
    val maxLength = 20
  }
  acct.withdraw(100)
}
