package impatient.chapter5

/**
  * 在嵌套类中，可以通过 外部类.this 的方式类访问外部类的this引用，就像Java那样。
  * 也可以用如下语法建立一个指向该引用的别名，如outer
  * @author Long Qiong 
  * @create 2018/12/2
  */
class Network2(val name: String, val age: Int) {
  class Member(val name:String, val age: Int){ outer=>
    def description = s"$name inside ${Network2.this.name}, $age inside ${outer.age}"
  }
}
