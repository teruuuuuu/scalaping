package com.teruuu.scalaping.scheduler

import java.util.Date

import akka.actor.{Actor, ActorLogging, Props}


class TaskExecutor extends Actor with ActorLogging{
  import TaskExecutor._

  val receiveActor = context.actorOf(TaskRunner.props, "taskRunner")

  def receive = {
    case Initialize =>
      log.info("starting MessagingActor")
    case "crawling" =>
      receiveActor !  SendMessage("start crawl")
  }
}

object TaskExecutor {
  val props = Props[TaskExecutor]
  case object Initialize
  case class SendMessage(text: String)
}