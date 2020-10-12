package impatient.chapter8

/**
 * 超类的构造
 * @param name
 * @param age
 * @param salary
 */
class Employee(name: String, age: Int, val salary: Double) extends Person(name, age){
//本例种Employee有三个参数：name,age,salary，其中的两个被“传递”到了超类
}
