package com.teruuu.scalaping.command.util

import java.sql.Timestamp
import java.time.LocalDateTime

import com.teruuu.scalaping.crawl.Scrap
import com.teruuu.scalaping.db.ScalapTop
import com.teruuu.scalaping.repository.{ScalapTopRepository, TopLinkRepository}

object AddTop {


  def main(args: Array[String]) = {
    if(args.length < 3) {
      println("URL, Title, Description")
    } else{
      val url = args(0)
      val title = args(1)
      val description = args(2)

      val id = ScalapTopRepository.add(url, title, description).toInt
      val links = Scrap.linkInfo(ScalapTop(id, url, Option(title), Option(description), Timestamp.valueOf(LocalDateTime.now())))

      val currentLinks = links._1
      currentLinks.foreach{ link =>
        TopLinkRepository.add(id, link.url, link.text.getOrElse(""))
      }
    }
  }
}
