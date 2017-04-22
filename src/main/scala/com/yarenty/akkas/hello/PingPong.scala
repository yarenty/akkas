package com.yarenty.akkas.hello

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by yarenty on 22/04/2017.
  */


case object PingMessage
case object PongMesasge
case object StartMessage
case object StopMessage



class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint{ count += 1; println("ping %s ->".format(count))}

  override def receive: Receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage

    case PongMesasge =>
      incrementAndPrint
      if (count>99) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        sender ! PingMessage
      }
  }
}

class Pong extends Actor {
  override def receive: Receive = {
    case PingMessage =>
      println(" <-  pong")
      sender ! PongMesasge
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
      println("and stop everything!!")
      System.exit(0)
  }
}


object PingPong extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name="pong")
  val ping = system.actorOf(Props(new Ping(pong)), name="ping")

  ping ! StartMessage

  ///System.exit(0) - this will
}