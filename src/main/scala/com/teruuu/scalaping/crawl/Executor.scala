package com.teruuu.scalaping.crawl

import com.teruuu.scalaping.repository.ScalapTopRepository

object Executor {

  def main(args: Array[String]): Unit ={
    run
  }

  def run = {
    ScalapTopRepository.lists.foreach{ top =>
      Scrap.trace(top)
    }
  }

}
