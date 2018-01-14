package com.teruuu.scalaping.scheduler

import akka.actor.ActorSystem
import com.teruuu.scalaping.crawl.CrawlInterface
import com.teruuu.scalaping.db.config.DbSetting
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension

object TaskScheduler extends App with DbSetting {
  CrawlInterface.init

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