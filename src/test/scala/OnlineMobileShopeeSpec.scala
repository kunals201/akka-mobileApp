import akka.actor.{Props, ActorSystem}
import akka.testkit._
import com.typesafe.config.ConfigFactory
import org.scalatest.{WordSpecLike, BeforeAndAfterAll, MustMatchers}


object OnlineMobileShopeeSpec {

  val testSystem = {
    val config = ConfigFactory.parseString(
      """
        |akka.loggers = [akka.testkit.TestEventListener]
      """.stripMargin
    )
    ActorSystem("test-system", config)
  }
}
import OnlineMobileShopeeSpec._


class OnlineMobileShopeeSpec extends TestKit(testSystem) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers with ImplicitSender {


  override protected def afterAll(): Unit = {
    system.terminate()
  }

  "A Validate " must {

    "respond with Error message in case customer request to buy more than 1 item" in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = ValidateHandler.prop.withDispatcher(dispatcherId)

      val ref = system.actorOf(props)
      EventFilter.warning(message = "Please purchase Item One at a Time", occurrences = 1).intercept {
        ref ! ((Customer("", "", "", ""), 2))
      }
    }
    "respond with success message when stock is not empty" in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = ValidateHandler.prop.withDispatcher(dispatcherId)

      val ref = system.actorOf(props)
      EventFilter.info(message = "Thanks for shopping at OnlineMobileShoppe", occurrences = 1).intercept {
        ref ! ((Customer("kunal", "", "", ""), 1))
      }
    }
    "Reply with 'Out Of Stock' if phones are out of  stock" in {
      val ref = TestActorRef[ValidateHandler]
      ref.underlyingActor.stockInStore = 0
      ref ! (Customer("kunal","noida","8447145214",""),1)
      expectMsg ("Item is Out Of Stock")
    }
  }


  "PurchaseActor" must {
    "respond with error message in case of wrong request" in {
      val dispatcherId = CallingThreadDispatcher.Id
      val props = PurchaseActor.prop(testActor).withDispatcher(dispatcherId)
      val ref = system.actorOf(props)
      EventFilter.info(message = "please enter valid input", occurrences = 1).intercept {
        ref ! "want mobile 1 mobile"
      }
    }

    "respond with if control is going to purchase handler" in {
      val ref = TestActorRef(PurchaseActor.prop(TestActorRef(PurchaseHandler.prop)))
      ref ! (Customer("kunal", "", "", ""), 1)
      expectMsg("Purchase Actor will tell to PurchaseHandler")
    }
  }

    "PurchaseHandler" must {
      "Reply with the same message it receives after telling" in {
        val ref = TestActorRef[PurchaseHandler]
        ref ! (Customer("kunal","noida","",""),1)
        expectMsg("Control will go to ValidateActor")
      }
    }

}







