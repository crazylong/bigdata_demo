package impatient.chapter7



/**
 * 包对象
 * 包可以包含类、对象、和特质，但不能包含函数或变量的定义。
 * 很不幸，这是Java虚拟机的局限性
 * 把工具函数或常量添加到包而不是某个Utils对象，这是更加合理的做法。
 * 包对象的出现正是为了解决这个局限性。
 * 每个包都可以有一个包对象。你需要在父包中定义它，且名称与子包一样。
 */

package object people{
  val defaultName = "John Q. Public"
}

package people {
  class Person {
    val name = defaultName //从包对象拿到的常量
  }
}

class PackageObject {
  def main(args: Array[String]): Unit = {
    print(people.defaultName)

    import impatient.chapter7.people.Person
    val person = new Person
    print(person.name)
  }
}
