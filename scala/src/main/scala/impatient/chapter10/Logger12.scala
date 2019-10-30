package impatient.chapter10

import java.io.IOException

import javax.swing.JFrame

/**
 * 扩展类的特质
 */
trait Logger12 {
  def log(msg: String)
}

trait ConsoleLogger12 extends Logger12{
  def log(msg: String): Unit = {
    println(msg)
  }
}

/**
 * Exception将会成为所有混入该特质的超类
 */
trait LoggedException12 extends Exception with ConsoleLogger12{
  def log(): Unit ={
    log{getMessage}
  }
}


/**
 * 特质的超类也自动成为我们的类的超类
 */
class UnhappyException12 extends LoggedException12{
  override def getMessage: String = "arggh!"
}

/**
 * 如果我们的类已经扩展了另一个类怎么办？没关系，只要那是特质的超类的一个子类即可。
 */
class UnhappyException13 extends IOException with LoggedException12{
  override def getMessage: String = "arggh!"
}


/**
 * 如果我们的类扩展自一个不相关的类，那么就不可能混入这个特质了，
 * 编译失败，我们无法同时将JFrame和Exception作为超类
 */
//class UnhappyException14 extends JFrame with LoggedException12{
//  override def getMessage: String = "arggh!"
//}



