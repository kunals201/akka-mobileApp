import akka.actor.ActorSystem
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory


object OnlineMobileShoppe extends App {

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /RouterPool    {
      |   router = balancing-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )
  val system = ActorSystem("RouterSystem", config)
  val purchaseRequest = system.actorOf(PurchaseHandler.prop)
  val purchaseActor = system.actorOf(FromConfig.props(PurchaseActor.prop(purchaseRequest)), "RouterPool")
  for (i <- 1 to 2080) {

    purchaseActor ! (Customer("kunal" + i, "Noida", "123556", "9876543210"), 1)

  }
}
