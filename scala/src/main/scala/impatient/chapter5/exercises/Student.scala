package impatient.chapter5.exercises

import scala.beans.BeanProperty

/**
  *
  * @author Long Qiong 
  * @create 2018/12/2
  */
class Student {
  @BeanProperty var name :String = ""
  @BeanProperty var id :Long = 0

  override def toString = s"Student(name=$name, id=$id)"
}
