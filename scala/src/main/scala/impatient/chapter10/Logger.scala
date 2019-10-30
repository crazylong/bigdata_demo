package impatient.chapter10

trait Logger {
  //特质中未被实现的方法默认就是抽象方法
  def log(msg: String)
}

//用extends而不是implements
//多个特质用with添加
//java接口都可以作为scala特质使用
class ConsoleLogger extends Logger with Cloneable with java.io.Serializable {

  //可以不写override
  override def log(msg: String): Unit = {
    println(msg)
  }
}
