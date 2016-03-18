package messages

/**
  * Created by knoldus on 16/3/16.
  */


case class Status(vehicleNo: Int)

case class Remove(vehicleNo: Int)


object ParkingLot {

  val slots=Array[Int](0,0,0,0,0)

  def getSlot()={
    if(slots.contains(0))
      true
    else
      false
  }

  def allot(vehicleNo: Int) = {
    val index=slots.indexOf(0)
    slots.update(index,vehicleNo)
  }

  def remove(vehicleNo: Int) = {
    val index=slots.indexOf(vehicleNo)
    slots.update(index,0)
  }
  def checkCar(vehicleId:Int)={
    if(slots.contains(vehicleId))
      true
    else
      false

  }
}
