package impatient.chapter10

/**
 * 特质中的具体字段
 * 对于特质中的每一个具体字段，使用改特质的类都会获得一个字段与之对应，
 * 这些字段不是被继承的；它们只是简单地被加到了子类中。
 */
trait Logger8 {
 def log(msg: String)
}

trait ConsoleLogger8 extends Logger8{
  override def log(msg: String): Unit = {
    println(msg)
  }
}

trait ShortLogger8 extends Logger8{
  val maxLength = 15
  abstract override def log(msg: String): Unit = {
    super.log(
      if(msg.length>maxLength) s"${msg.substring(0, maxLength-3)}..."
      else msg)
  }
}

class SavingAccount8 extends Account with ConsoleLogger8 with ShortLogger8{
  var interest = 0
  def withdraw(amount: Double): Unit ={
    if(amount>balance) log("Insufficient funds")
    else balance -= amount
  }
}

object Main8 extends App{
  val acct = new SavingAccount8
  acct.withdraw(100)
}

//balance 超类字段
//interest/maxLength 子类字段
//javap -private SavingAccount8.class


