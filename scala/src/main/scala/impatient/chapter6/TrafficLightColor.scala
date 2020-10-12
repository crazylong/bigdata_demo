package impatient.chapter6

/**
  * 枚举
  * 和Java不同，Scala并没有枚举类型，
  * 不过，标准类库提供了一个Enumeration助手类，可以用于产出枚举
  */
object TrafficLightColor extends Enumeration {
  //记住枚举的类型时TrafficLightColor.Value而不是TrafficLightColor
  //后者是握有这些值的对象。
  //有人推荐增加一个类型别名：
  type TrafficLightColor = Value
  //现在枚举类型变成了 TrafficLightColor.TrafficLightColor，
  //但仅当你使用了import语句时这样做才显得有意义。
  //import TrafficLightColor._



  //1.每次调用Value方法都返回内部类的新实例，该内部类也叫做Value
  //val Red, Yellow, Green = Value

  //2.或者，也可以向Value方法传入ID，名称，或者两个参数都传：
  val Red = Value(0, "Stop")
  val Yellow = Value(10)//名称为"Yellow"
  val Green = Value("GO")//ID为11
}
