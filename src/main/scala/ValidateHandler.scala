import akka.actor.{Actor, Props}
//import PurchaseActor.stockInStore
import akka.event.Logging

class ValidateHandler extends Actor {
  var stockInStore = 2000

  val log = Logging(context.system, this)

  override def receive = {
    case (obj: Customer, itemsWantToPurchase: Int) => {
      if (itemsWantToPurchase > 1) {
        log.warning("Please purchase Item One at a Time")
      }
      else {
        if (stockInStore > 0) {
          stockInStore -= 1 //itemsWantToPurchase
          log.info("Thanks for shopping at OnlineMobileShoppe")
          log.info(s"Customer name:- ${obj.name} \n ${obj.mobileNumber}")
        }
        else {
          log.info("Item has been sold Out We will notify you when stock will come.....")
          sender() ! "Item is Out Of Stock"
        }
      }
    }
  }
}

object ValidateHandler {
  def prop: Props = Props[ValidateHandler]
}