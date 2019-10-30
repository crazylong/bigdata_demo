package impatient.chapter10

import java.io.PrintWriter

/**
 * 特质构造顺序
 * 特质也可以有构造器，其由字段的初始化和其它特质体中的语句组成
 * 1.首先调用超类的构造器
 * 2.特质构造器在超类构造器之后、类构造器之前
 * 3.特质构造器从左到右构造
 * 4.在每个特质中，父特质被优先构造；
 * 5.如果多个特质共有一个父特质，父特质只被构造一次
 * 6.所有特质构造完毕，子类被构造
 */
trait Logger10 {
  def log(msg: String)
}

trait FileLogger10 extends Logger10{
  val out = new PrintWriter("app.log") //这是特质构造器的一部分
  out.println(s"# ${java.time.Instant.now()}") //这同样是特质构造器的一部分

  def log(msg: String){out.println(msg); out.flush()}
}


trait ShortLogger10 extends Logger10{
  val maxLength = 15
  abstract override def log(msg: String): Unit = {
    super.log(
      if(msg.length>maxLength) s"${msg.substring(0, maxLength-3)}..."
      else msg)
  }
}

class SavingAccount10 extends Account with FileLogger10 with ShortLogger10{
  //1.Account
  //2.Logger10
  //3.FileLogger10
  //4.ShortLogger10
  //5.SavingAccount10
}