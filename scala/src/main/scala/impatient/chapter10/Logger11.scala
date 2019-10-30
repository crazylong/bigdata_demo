package impatient.chapter10

import java.io.PrintStream

/**
 * 初始化特质中的字段
 * 特质不能有构造器参数，每个特质都有一个无参的构造器。
 * 缺少构造器参数是特质与类之间唯一的技术差别。
 */
trait Logger11 {
 def log(msg: String)
}

trait FileLogger11 extends Logger11 {
  println("FileLogger11")
  val fileName: String
  //val out = new PrintStream(fileName)

  //3.懒值
  lazy val out = new PrintStream(fileName)

  def log(msg:String) {
    out.println(msg);
    out.flush()}
}

abstract class SavingAccount11 extends Account with Logger11{
  println("SavingAccount11")
  def withDraw(amount: Double): Unit ={
    if(amount > balance) log("Insufficient funds")
    else balance -= amount
  }
}

object Main11 extends App{
  //1.
  //这种方式表面上看给fileName赋值了，实际运行会提示空指针
  //主要是以为构造顺序的原因，new语句可以看成生成了一个匿名子类，超类为SavingAccount11，混入FileLogger11特质
  //所以匿名子类构造fileName是在FileLogger11构造完之后赋值的
//  val acct = new SavingAccount11 with FileLogger11{
//    println("匿名子类")
//    override val fileName: String = "app.log"
//  }


  //2.提前定义
//  val acct = new {
//    val fileName = "app.log"
//  } with SavingAccount11  with FileLogger11


  //3.懒值
  //在FileLogger11中设置 lazy val out = new PrintStream(fileName)
  //这样只会在执行log方法的时候才用到out变量，此时fileName构造完成
  val acct = new SavingAccount11  with FileLogger11{
    println("匿名子类")
    override val fileName: String = "app.log"
  }


  acct.withDraw(100)
}
