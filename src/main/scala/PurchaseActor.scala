import akka.actor.{Actor, ActorRef, Props}
import OnlineMobileShoppe.purchaseRequest

class PurchaseActor(requestHandlerRefrence:ActorRef) extends Actor{


  override def receive = {
    case (obj:Customer,items:Int) => requestHandlerRefrence ! (obj,items)
  }
}

object PurchaseActor{

  def prop(RequestHandlerRef: ActorRef):Props = Props(classOf[PurchaseActor],purchaseRequest)
  var stockInStore=1000
}
