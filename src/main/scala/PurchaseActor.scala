import akka.actor.{Actor, ActorRef, Props}
import OnlineMobileShoppe.purchaseRequest
import akka.event.Logging

class PurchaseActor(requestHandlerRefrence:ActorRef) extends Actor{
  val log=Logging(context.system,this)

  override def receive = {
    case (obj:Customer,items:Int) => requestHandlerRefrence ! (obj,items)
      sender() ! "Purchase Actor will tell to PurchaseHandler"
    case _ =>log.info("please enter valid input")
  }
}

object PurchaseActor{

  def prop(RequestHandlerRef: ActorRef):Props = Props(classOf[PurchaseActor],purchaseRequest)
}
