package com.teruuu.scalaping.scheduler

import akka.actor.{Actor, ActorLogging, Props}
import com.teruuu.scalaping.crawl.Executor

class TaskRunner extends Actor with ActorLogging{
  def receive = {
    case TaskExecutor.SendMessage(txt: String) =>
      Executor.run
  }

}

object TaskRunner {
  val props = Props[TaskRunner]
}