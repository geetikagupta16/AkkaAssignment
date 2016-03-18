import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import messages._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by knoldus on 16/3/16.
  */

class Attendent(monitor: ActorRef) extends Actor {

  def receive = {

    case Status(vehicleNo) => {
      monitor ! Status(vehicleNo)
    }
    case Remove(vehicleNo) => monitor ! Remove(vehicleNo)

  }
}

class Monitor extends Actor {
  def receive = {
    case Status(vehicleNo) =>
      if (ParkingLot.getSlot()) {
        ParkingLot.allot(vehicleNo)
        println("Alloted:" + vehicleNo)
      }
      else
        println("No Space...")

    case Remove(vehicleId) => if (ParkingLot.checkCar(vehicleId) == false)
      println("No car with this number")
    else {
      ParkingLot.remove(vehicleId)
      println("Removed :" + vehicleId)

    }
  }
}

object ParkingMain {

  val system = ActorSystem("ParkingMain")
  val monitor = system.actorOf(Props[Monitor], "ParkingMonitor")
  val actor = system.actorOf(Props(new Attendent(monitor)), "ParkingAttendent")
  implicit val timeout = Timeout(2 seconds)

  def main(args: Array[String]): Unit = {

    park(1234)
    park(234)
    leave(1234)
    park(345)
    park(5673)
    leave(8768)
    park(3245)
    park(3456)
    park(45634)
    system.shutdown()
  }

  def park(vehicleNo: Int): Unit = {
    (actor ? Status(vehicleNo))
    Thread.sleep(100)
  }

  def leave(vehicleNo: Int) = {
    (actor ? Remove(vehicleNo))
  }

}


