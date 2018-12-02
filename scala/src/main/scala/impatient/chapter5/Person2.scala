package impatient.chapter5

/**
  * 辅助构造器
  * 1.辅助构造器的名称为this
  * 2.每一个辅助构造器都必须以一个对先前已定义的其它辅助构造器或主构造器的调用开始
  * @author Long Qiong
  * @create 2018/12/2
  */
class Person2 {
  private var name = ""
  private var age = 0

  def this(name:String){//一个辅助构造器
    this()//调用主构造器，一个类如果没有显示定义主构造器，则自动拥有一个无参的主构造器
    this.name = name
  }

  def this(name:String, age:Int){//另一个辅助构造器
    this(name)//调用前一个辅助构造器
    this.age = age
  }
}

