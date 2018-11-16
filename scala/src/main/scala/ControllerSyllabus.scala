import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/10/6
  */
object ControllerSyllabus {
  def main(args: Array[String]): Unit = {
    val a1=10
    val a2=if(a1>20){
      "a1大于20"
    } else {
      "a1小于20"
      println()
    }

    println(a2)

    val a3=if(a1>5){
      30
    } else {
      "a1小于20"
    }
    println(a3)

    val a4 : Int=if(a1>5){
      30
    } else {
      //"a1小于20"
      20
    }
    println(a4)


    println("**************************while")
    var n=1
    val while1=while(n<=10){
      n += 1
    }
    println(while1)
    println(n)



    println("*************while中断*****")
    n=0
    import scala.util.control.Breaks
    val loop=new Breaks
    loop.breakable{
      while(n<=20){
        n +=1
        if(n==18){
          loop.break()
        }
      }
    }
    println(n)
  }


}
