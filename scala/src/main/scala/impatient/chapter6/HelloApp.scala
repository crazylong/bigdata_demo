package impatient.chapter6

/**
  * 应用程序对象
  * 每个Scala程序都必须从一个对象的main方法开始，这个方法的类型为Array[String]=> unit
  */
//object HelloApp {
//  def main(args: Array[String): Unit ={
//    println("Hello World!")
//  }
//}

//除了每次都提供自己的mian方法，你还可以扩展App特质，然后将程序代码放入构造器方法体内
object HelloApp extends App{
  //如果需要命令行参数，可以通过args属性得到：
  if(args.length > 0)
    println(f"Hello ${args(0)}")
  else
    println("Hello, World")
}

//如果在调用该应用程序时设置了scala.time选项，程序退出时会显示逝去的时间
// $ scalac HelloApp.scala
// $ scala -Dscala.time HelloApp Fred

