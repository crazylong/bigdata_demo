package impatient.chapter10

trait Logger6 {
  def log(msg: String)
}

trait TimestampLogger6 extends Logger6{
  //编译报错，父类没有方法实现
//  override def log(msg: String): Unit = {
//    super.log(s"${java.time.Instant.now()} $msg")
//  }

  //Scala认为TimestampLogger依旧时抽象的--它需要混入一个具体的log方法，
  //因此你必须给方法打上abstract关键字以及override关键字
  abstract override def log(msg: String): Unit = {
    super.log(s"${java.time.Instant.now()} $msg")
  }
}

