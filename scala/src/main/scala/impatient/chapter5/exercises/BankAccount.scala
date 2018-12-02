package impatient.chapter5.exercises

import org.junit.Test

/**
  *
  * @author Long Qiong 
  * @create 2018/12/2
  */
class BankAccount() {
  private var account :BigDecimal = 0
  def deposit(moneys: BigDecimal)={
    account += moneys
  }

  def withdraw(moneys:BigDecimal)={
    //balance -= moneys
    if(moneys <= balance)
      account -= moneys
    else
      throw new Exception("Your balance (" + account + ") too small for withdraw " + moneys)
  }

  def balance = account


}


