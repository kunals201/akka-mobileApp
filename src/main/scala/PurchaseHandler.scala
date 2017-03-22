import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

class PurchaseHandler extends Actor {
  val log = Logging(context.system, this)


  val messageActor: ActorRef = context.actorOf(ValidateHandler.prop)

  override def receive = {
    case (obj: Customer, numberOfitems: Int) => {

      sender ! "Control will go to ValidateActor"
      messageActor ! (obj, numberOfitems)
    }
  }
}

object PurchaseHandler {

  def prop: Props = Props[PurchaseHandler]

}
