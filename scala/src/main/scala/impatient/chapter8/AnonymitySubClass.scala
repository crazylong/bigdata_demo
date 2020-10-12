package impatient.chapter8

/**
 * 匿名子类
 */
class AnonymitySubClass {

  def main(args: Array[String]): Unit = {
    val alien = new Person("Fred") {
      def greeting = "Greetings, Earthling! My name is Fred."
    }

    /**
     * Person{def greeting: String} 结构类型 Structural Type，作为参数
      * @param p
     */
    def meet(p: Person{def greeting: String}) {
      println(p.name + " says: " + p.greeting)
    }

    meet(alien)
  }

  class Person(val name: String){
    override def toString = getClass.getName + "[name=" + name + "]"
  }
}






