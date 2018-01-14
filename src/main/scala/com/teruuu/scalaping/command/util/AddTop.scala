package com.teruuu.scalaping.command.util

import com.teruuu.scalaping.crawl.{CrawlInterface, ScalapEntity}
import com.teruuu.scalaping.db.config.DbSetting

object AddTop  extends DbSetting {

  def main(args: Array[String]) = {
    if(args.length < 3) {
      println("URL, Title, Description")
    } else{
      val url = args(0)
      val title = args(1)
      val description = args(2)

      CrawlInterface.addTop(url, title, description)
    }
  }
}
