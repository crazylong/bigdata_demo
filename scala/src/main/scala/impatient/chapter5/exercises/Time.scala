package impatient.chapter5.exercises

/**
  *
  * @author Long Qiong 
  * @create 2018/12/2
  */
class Time(val hours:Int, val minutes: Int) {
  def before(other: Time):Boolean = {
    if(hours > other.hours)
      false
    else if(hours == other.hours && minutes > other.minutes)
      false
    else
      true
  }
}
