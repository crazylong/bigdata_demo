import org.scalatest.FunSuite

/**
  *
  * @author Long Qiong 
  * @create 2018/10/6
  */
class ControlTest extends FunSuite{
  def test_Add() ={
    val util = new TestUtils
    print(util.Add(1,2))
  }

  test("testFor"){
    for(i<-1 to 3;j<-1 to 3){
      print(i*j+" ")
    }
    println()
  }
}
