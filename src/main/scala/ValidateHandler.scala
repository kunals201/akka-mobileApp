import akka.actor.{Actor, Props}
//import OnlineMobileShoppe.remainingStock
import akka.event.Logging
import PurchaseActor.stockInStore
/**
  * Created by knoldus on 3/21/17.
  */
class ValidateHandler extends Actor {
  //var remainingStock=100
  val log=Logging(context.system,this)
  override def receive = {
    case (obj:Customer,itemsWantToPurchase:Int) => {
      if (itemsWantToPurchase > 1) {
        log.warning("Please purchase Item One at a Time ")
      }
      else {
        if (stockInStore > 0) {
          stockInStore -= 1//itemsWantToPurchase
          log.info( s"Thanks for shopping at OnlineMobileShoppe ${obj.name} \n ${obj.mobileNumber} ")
        }
        else {
          log.info("Item has been sold Out We will notify you when stock will come.....")
        }
      }
    }
  }
}
object ValidateHandler {
  def prop: Props = Props[ValidateHandler]
}