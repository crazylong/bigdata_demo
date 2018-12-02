package impatient.chapter5

import java.io.FileReader
import java.util.Properties

/**
  * 主构造器
  * 1.主构造器的参数直接放置在类名之后，主构造器的参数被编译成字段，其值被初始化成构造时传入的参数
  * 2.主构造器会执行类中定义的所有语句
  * 3.通常可以通过在主构造器中使用默认参数来避免过多的使用辅助构造器，如 val sex: Int = 0
  * 4.构造参数也可以是普通的方法参数，不带val 或var。这样的参数如何处理取决于它们在类中如何被使用
  * 5.Martin Odersky建议这样来看待主构造器：在Scala中，类也接受参数，就像方法一样
  * @author Long Qiong
  * @create 2018/12/2
  */
class Person3(val name: String, val age: Int, val sex :Int = 0, school: String, score:Int) {
  //主构造器会执行类中定义的所有语句，如：
  println("Just constructed another person")
  //println语句是主构造器的一部分。每当有对象被构造出来时，上述代码就会被执行


  def description = s"$name is $age years old"

  //当你需要在构造过程中配置某个字段时，这个特性特别有用。如：
  private val props = new Properties()
  props.load(new FileReader("E:\\books\\大数据\\bigdata_demo\\scala\\src\\main\\resources\\scala.properties"))
  //上述代码是主构造器的一部分


  //如果不带val或var的参数至少被一个方法所使用，它将升格为字段
  //否则，改参数将不被保存为字段。它仅仅是一个可以被主构造器中的代码访问的普通参数
  //
  def description2 = "school = " + school + ", score = " + score
  //上述代码声明并初始化了不可变字段name和age，而这两个字段都是对象私有的，
  //类似于这样的字段等同于private[this] val 字段的效果


}
