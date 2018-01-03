package com.teruuu.scalaping.repository

import java.sql.Timestamp
import java.time.LocalDateTime

import com.teruuu.scalaping.db.{ScalapTop, TopLink}
import scalikejdbc.DB

object TopLinkRepository {

  def lists(): List[TopLink] = {
    DB readOnly  { implicit session =>
      TopLink.all
    }
  }

  def selectByTopId(top_id: Int): List[TopLink] = {
    DB readOnly  { implicit session =>
      TopLink.selectByTopId(top_id)
    }
  }

  def add(top_id: Int, url: String, text:String):Long = {
    DB localTx  { implicit session =>
      TopLink.insert(top_id, url, text, Timestamp.valueOf(LocalDateTime.now()))
    }
  }

  def delete(id: Int) = {
    DB localTx { implicit session =>
      TopLink.delete(id)
    }
  }

}
