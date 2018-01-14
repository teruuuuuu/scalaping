package com.teruuu.scalaping.scheduler

import akka.actor.{Actor, ActorLogging, Props}
import com.teruuu.scalaping.crawl.CrawlInterface

class TaskRunner extends Actor with ActorLogging{
  def receive = {
    case TaskExecutor.SendMessage(txt: String) =>
      CrawlInterface.run //クロール開始
  }
}

object TaskRunner {
  val props = Props[TaskRunner]
}