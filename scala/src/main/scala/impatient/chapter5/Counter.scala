package impatient.chapter5

/**
  * 在scala中，类并不声明为public.Scala源文件可以包含多个类，
  * 所有这些类都具有公有可见性
  * @author Long Qiong 
  * @create 2018/12/1
  */
class Counter {
  private var value = 0 //必须初始化字段

  def increment() {//方法默认是公有的
    value += 1
  }

  def current() = value
  def current2 = value
  //无参方法，可以写上圆括号，也可以不写
  //建议对于改值器方法（即改变对象状态的方法）使用()，对于取值器方法（不会改变对象状态的方法）去掉()
}
