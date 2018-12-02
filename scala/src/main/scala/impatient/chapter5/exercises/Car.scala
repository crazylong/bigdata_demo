package impatient.chapter5.exercises

/**
 * @author Long Qiong
 * @create 2018/12/2
 */
class Car(val supply: String, val modelName: String, val modelYear: Int = -1, var carNo : String = "") {
  override def toString = s"Car(supply=$supply, modelName=$modelName, modelYear=$modelYear, carNo=$carNo)"
}
