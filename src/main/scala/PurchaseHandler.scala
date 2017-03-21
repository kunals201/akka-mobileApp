import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration.DurationInt
/**
  * Created by knoldus on 3/21/17.
  */
class PurchaseHandler extends Actor {
  val log = Logging(context.system, this)


  val messageActor:ActorRef = context.actorOf(ValidateHandler.prop)

  override def receive = {
    case (obj: Customer, numberOfitems: Int) => {
      //    log.info("go to validate Actor to check stock is available or not")
      implicit val timeout = Timeout(1000 seconds)
      messageActor ! (obj, numberOfitems)
    }
  }
}
object PurchaseHandler{

  def prop:Props = Props[PurchaseHandler]

}
