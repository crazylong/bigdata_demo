package impatient.chapter6

/**
  * 一个object可以扩展类以及一个或多个特质，其结果是一个扩展了指定类以及特质的类的对象，同时拥有在对象定义中给出的所有特性
  * @author Long Qiong 
  * @create 2018/12/3
  */
object DoNothingAction extends UndoableAction ("Do nothing"){
  override def undo(): Unit = {}

  override def redo(): Unit = {}
}
