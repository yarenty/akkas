package com.yarenty.akkas.hello

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by yarenty on 22/04/2017.
  */
class HelloActor extends Actor{
  override def receive: Receive = {
    case "hello" => println("hello back at you")
    case _ => println("huh???")
  }
}

class HelloNamedActor(name:String) extends Actor{
  override def receive: Receive = {
    case "hello" => println("hello back from %s".format(name))
    case _ => println("huh [%s]???".format(name))
  }
}


object HelloMain extends  App {

  val system = ActorSystem("HelloSystem")

  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  val helloNamedActor = system.actorOf(Props(new HelloNamedActor("Stefan")), name = "hellonamedactor")


  helloActor ! "hello"
  helloActor ! "buenos dias"

  helloNamedActor ! "hello"
  helloNamedActor ! "buenos dias"

}