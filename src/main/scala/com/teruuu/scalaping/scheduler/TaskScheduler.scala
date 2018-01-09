package com.teruuu.scalaping.scheduler

import akka.actor.ActorSystem
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension

object TaskScheduler extends App {

  val _system = ActorSystem("system")
  // スケジューラを生成
  val scheduler = QuartzSchedulerExtension(_system)
  val messagingActor = _system.actorOf(TaskExecutor.props, "crawl")
  scheduler.schedule(
    "CrawlTask",
    messagingActor,
    "crawling"
  )
}