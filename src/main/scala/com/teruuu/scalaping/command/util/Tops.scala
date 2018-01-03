package com.teruuu.scalaping.command.util

import com.teruuu.scalaping.repository.ScalapTopRepository

object Tops {
  def main(args: Array[String]) = {
    val list = ScalapTopRepository.lists.map(_.toView)
    list.foreach(println)
  }
}
