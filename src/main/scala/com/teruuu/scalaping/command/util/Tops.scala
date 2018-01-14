package com.teruuu.scalaping.command.util

import com.teruuu.scalaping.crawl.{CrawlInterface, CrawlService}
import com.teruuu.scalaping.db.config.DbSetting

object Tops extends DbSetting {
  def main(args: Array[String]) = CrawlInterface.showTopInfo
}
