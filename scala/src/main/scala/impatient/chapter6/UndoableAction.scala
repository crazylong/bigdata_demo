package impatient.chapter6

/**
  *
  * @author Long Qiong 
  * @create 2018/12/3
  */
abstract class UndoableAction(val desc: String) {
  def undo():Unit
  def redo():Unit
}
