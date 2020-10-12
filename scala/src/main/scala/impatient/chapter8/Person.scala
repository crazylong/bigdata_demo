package impatient.chapter8

class Person(val name:String, var age: Int) {
  override def toString: String = s"${getClass.getName}[name=$name]"
}

class SecretAgent(codeName:String, age: Int) extends Person(codeName, age){
  override val name: String = "secret"

  //override def toString: String = "secret"
}
