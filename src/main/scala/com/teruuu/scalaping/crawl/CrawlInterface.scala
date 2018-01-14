package com.teruuu.scalaping.crawl

// インターフェース
object CrawlInterface {
  def main(args: Array[String]): Unit ={
    run
  }

  def run = CrawlService.allTops.map(new ScalapEntity(_)).foreach(_.trace)

  def init = {
    CrawlService.allTops.map(new ScalapEntity(_)).foreach(_.runInit)
  }

  def addTop(url: String, title: String, description: String) = {
    val scalap = ScalapEntity(url, title, description)
    scalap.addTop
  }

  def showTopInfo() = {
    CrawlService.allTops.foreach(println)
  }
}
