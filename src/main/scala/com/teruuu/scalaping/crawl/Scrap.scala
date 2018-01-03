package com.teruuu.scalaping.crawl

import java.sql.Timestamp
import java.time.LocalDateTime

import com.teruuu.scalaping.db.{ScalapTop, TopLink}
import com.teruuu.scalaping.repository.TopLinkRepository
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

    val newLinks = currentLinks.filter(currentLink =>
      savedLinks.find{savedLink => savedLink.url.equals(currentLink.url)} match {
        case Some(x) => false
        case None => true
      }
    )

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
  }
}
