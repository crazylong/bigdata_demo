package impatient.chapter6

/**
  * 单例对象
  * Scala没有静态方法或静态字段，可以用object这个语法结构来达到同样的目的
  * 对象定义了某个类的单个实例，包含了你想要的特性
  * @author Long Qiong 
  * @create 2018/12/3
  */
object Accounts {
  private var lastNumber = 0
  def newUniqueNumber()= {lastNumber += 1; lastNumber}
  //对象的构造器在该对象第一次被使用时调用。在本例中，Accounts的构造器在Accounts.newUniqueNumber()的首次调用时执行。

  //对于任何你在Java或C++中会使用单例对象的地方，在Scala中都可以用对象来实现：
  //1.作为存放工具函数或常量的地方
  //2.高效地共享单个不可变实例
  //3.需要用单个实例来协调某个服务时（参考单例模式）
}
