package com.teruuu.scalaping.crawl

import java.sql.Timestamp
import java.time.LocalDateTime

import com.teruuu.scalaping.config.ApplicationConfig
import com.teruuu.scalaping.db.{ScalapTop, TopLink}
import com.teruuu.scalaping.repository.TopLinkRepository
import com.teruuu.scalaping.slack.SlackCaller
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.JavaConversions._

object Scrap {


  def trace(scalapTop: ScalapTop) = {
    val savedLinks = TopLinkRepository.selectByTopId(scalapTop.id)
    val now = Timestamp.valueOf(LocalDateTime.now())
    val monthAgo = Timestamp.valueOf(LocalDateTime.now.minusMonths(1))
    val doc = Jsoup.connect(scalapTop.url).get
    val links = doc.select("a[href]")

    val currentLinks = links.toIterable.map{ link =>
      TopLink(0, scalapTop.id, link.attr("abs:href").trim, Option(link.text.trim), now)
    }
    val savedUrls = savedLinks.map(_.url)

    val newLinks = currentLinks.foldLeft(List.empty[TopLink]){ (acc, v)  => v match {
        case v if savedUrls.contains(v.url) || acc.map(_.url).contains(v.url) => acc
        case v => acc :+ v
      }
    }

    val deletedLinks = savedLinks.filter(savedLink =>
      currentLinks.find{currentLink => currentLink.url.equals(savedLink.url)} match {
        case Some(x) => false
        case None => true
      }
    )

    newLinks.foreach{ link =>
      TopLinkRepository.add(scalapTop.id, link.url, link.text.getOrElse(""))
    }

    deletedLinks.foreach { link =>
      if(link.add_date.before(monthAgo))
        TopLinkRepository.delete(link.id)
    }

    if(newLinks.length >= 1){
      SlackCaller.call(ApplicationConfig.slack_token, ApplicationConfig.slack_channel, newLinks.map(link => link.url).mkString("\n"))
    }
  }
}
