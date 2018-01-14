package com.teruuu.scalaping.crawl

import java.sql.Timestamp
import java.time.LocalDateTime

import com.teruuu.scalaping.config.ApplicationConfig
import com.teruuu.scalaping.db.{ScalapTop, TopLink}
import com.teruuu.scalaping.slack.SlackCaller
import org.jsoup.Jsoup
import scalikejdbc.DB

import scala.collection.JavaConversions._

// トランザクションの管理や外部サービスの呼び出し
object CrawlService {

  def allTops:List[ScalapTop] = {
    DB readOnly { implicit session =>
      ScalapTop.all
    }
  }

  def linkInfo(scalapTop: ScalapTop):(List[TopLink], List[TopLink]) = {
    DB readOnly { implicit session =>
      val savedLinks = TopLink.selectByTopId(scalapTop.id)
      val now = Timestamp.valueOf(LocalDateTime.now())
      val monthAgo = Timestamp.valueOf(LocalDateTime.now.minusMonths(1))
      val doc = Jsoup.connect(scalapTop.url).get
      val links = doc.select("a[href]")

      val currentLinks = links.toIterable.map { link =>
        TopLink(0, scalapTop.id, link.attr("abs:href").trim, Option(link.text.trim), now)
      }
      val savedUrls = savedLinks.map(_.url)

      val newLinks = currentLinks.foldLeft(List.empty[TopLink]) { (acc, v) =>
        v match {
          case v if savedUrls.contains(v.url) || acc.map(_.url).contains(v.url) => acc
          case v => acc :+ v
        }
      }

      val deletedLinks = savedLinks.filter(savedLink =>
        currentLinks.find { currentLink => currentLink.url.equals(savedLink.url) } match {
          case Some(x) => false
          case None => true
        }
      )
      (newLinks, deletedLinks)
    }
  }


  def topLinkInsertDelete(top_id: Int, newLinks: List[TopLink], deletedLinks: List[TopLink]) = {
    val monthAgo = Timestamp.valueOf(LocalDateTime.now.minusMonths(1))

    DB localTx  { implicit session =>
      newLinks.foreach{ link =>
        TopLink.insertTopLink(top_id, link.url, link.text.getOrElse(""), Timestamp.valueOf(LocalDateTime.now()))
      }
      deletedLinks.foreach { link =>
        if(link.add_date.before(monthAgo))
          TopLink.delete(link.id)
      }
    }
    if(newLinks.length >= 1){
      SlackCaller.call(ApplicationConfig.slack_token, ApplicationConfig.slack_channel, newLinks.map(link => link.url).mkString("\n"))
    }
  }

  def addTops(scalapTop: ScalapTop, newLinks: List[TopLink]) = {
    DB localTx  { implicit session =>
      val id = ScalapTop.insertScalapTop(scalapTop.url, scalapTop.title.get, scalapTop.description.get, scalapTop.create_date).toInt
      newLinks.foreach{ link =>
        TopLink.insertTopLink(id, link.url, link.text.getOrElse(""), Timestamp.valueOf(LocalDateTime.now()))
      }
    }
  }
}
