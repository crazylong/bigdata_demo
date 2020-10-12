package impatient.chapter8

class Item(val description: String, val price: Double) {
//  final override def equals(other: Any) = {
//    other.isInstanceOf[Item] && {
//      val that = other.asInstanceOf[Item]
//      that.description == description && that.price == price
//    }
//  }


  final override def equals(other: Any) = other match {
    case that: Item => description == that.description && price == that.price
    case _ => false
  }

  final override def hashCode(): Int = (description, price).##
  //##方法是hashCode的null值安全版本，对null交出0，而不是抛出异常
}
