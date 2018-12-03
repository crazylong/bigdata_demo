package impatient.chapter6

/**
  * 伴生(companion)对象
  * 在Java或C++中，通常会用到既有实例方法又有静态方法的类。
  * 在Scala中，可以通过类与类同名的“伴生(companion)”对象来达到同样的目的
  * @author Long Qiong 
  * @create 2018/12/3
  */
class Account(val id: Int, initialBalance: Double) {
 val id= Account.newUniqueNumber()
  private var balance = 0.0
  def deposit(amount: Double){balance += amount}
}

/**
  * 伴生对象
  * 类和它的伴生对象可以相互访问私有特性，它们必须存在于同一个源文件中
  * 注意，类的伴生对象的功能特性并不在类的作用域内
  * 在REPL中，要同时定义类和对象，必须用粘贴模式。键入:paste
  */
object Account{
  private var lastNumber = 0
  def newUniqueNumber()= {lastNumber += 1; lastNumber}

  //我们通常会定义和使用对象的apply方法。
  //当遇到如下形式的表达式时 Object(参数1,....,参数N)，apply方法就会被调用，
  //通常，这样一个apply方法返回的是伴生类的对象
  //如:Array("Mary", "had", "a")
  //为什么不用构造器呢？对应嵌套表达式而言，省去new关键字会方便很多，
  def apply(initialBalance:Double)=
    new Account(newUniqueNumber(), initialBalance)
}
