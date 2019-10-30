package impatient.chapter10

trait Logger14 {
  def log(msg: String)
}

//scalac Logger14.scala
//javap -private Logger14
//翻译成java形式接口
/**
public interface impatient.chapter10.Logger14 {
  public abstract void log(java.lang.String);
}
 */


trait ConsoleLogger14 extends Logger14 {
  def log(msg:String){println(msg)}
}
//javap -private ConsoleLogger14
//trait方法对应java接口默认方法(原书这么写的，没看出来)
/**
public interface impatient.chapter10.ConsoleLogger14 extends impatient.chapter10.Logger14 {
  public abstract void log(java.lang.String);
}
 */


/**
 * trait字段对应java getter setter方法
 * 书上有$init$()方法，这里并没有看到
 */
trait ShortLogger14 extends Logger14 {
  val maxLength = 15

  abstract override def log(msg: String) {
    super.log(
      if (msg.length <= maxLength) msg
      else s"${msg.substring(0, maxLength - 3)}...")
  }
}

/**
public interface impatient.chapter10.ShortLogger14 extends impatient.chapter10.Logger14 {
  public abstract void impatient$chapter10$ShortLogger14$_setter_$maxLength_$eq(int);
  public abstract void impatient$chapter10$ShortLogger14$$super$log(java.lang.String);
  public abstract int maxLength();
  public abstract void log(java.lang.String);
}
 */

class Account14 {
  protected var balance = 0.0
}


/**
 * 当特质被混入类时，对应的类会得到一个maxLength字段，
 * 并且getter和setter方法也被定义用于获取和设置字段值。
 */
class SavingAccount14 extends Account14 with ConsoleLogger14 with ShortLogger14{
  def withdraw(amount: Double): Unit ={
    if(amount > balance) log("Insufficient funds")
    else balance -= amount
  }
}

/**
public class impatient.chapter10.SavingAccount14 extends impatient.chapter10.Account14 implements impatient.chapter10.ConsoleLogger14,impatient.chapter10.ShortLogger14 {
  private final int maxLength;
  public int maxLength();
  public void impatient$chapter10$ShortLogger14$$super$log(java.lang.String);
  public void impatient$chapter10$ShortLogger14$_setter_$maxLength_$eq(int);
  public void log(java.lang.String);
  public void withdraw(double);
  public impatient.chapter10.SavingAccount14();
}
 */



