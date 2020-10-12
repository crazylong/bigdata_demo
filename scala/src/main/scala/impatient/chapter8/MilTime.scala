package impatient.chapter8

/**
 * 对只持有一个值的对象分配空间并不是高效的做法。
 * 值类(value class)允许你定义“内联（inline）”的类，
 * 这样一来对应字段的值就可以直接被使用
 * 值类具有如下性质：
 * 1.扩展自AnyVal
 * 2.主构造器有且只有一个参数，该参数是一个val，且没有方法体
 * 3.没有其它字段或构造器
 * 4.自动提供的equals与hashCode方法比较和散列(hash)背后对应的那个值
 * @param time
 */
class MilTime private(val time: Int) extends AnyVal{
  def minutes = time % 100
  def hours = time / 100

  override def toString = f"$time"
}

object MilTime{
  def apply(t: Int) =
    if(0<=t && t<2400 && t%100<60) new MilTime(t)
    else throw new IllegalArgumentException
}


//class MilTime private(val time: Int) extends AnyVal {
//  def minutes = time % 100
//  def hours = time / 100
//  override def toString = f"$time04d"
//}