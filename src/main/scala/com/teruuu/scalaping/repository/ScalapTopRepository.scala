package com.teruuu.scalaping.repository

import java.sql.Timestamp
import java.time.LocalDateTime

import com.teruuu.scalaping.db.ScalapTop
import com.teruuu.scalaping.db.config.{DbSetting, LogSettings}
import scalikejdbc.{AutoSession, DB}

object ScalapTopRepository extends DbSetting with LogSettings{

  def lists(): List[ScalapTop] = {
    DB readOnly  { implicit session =>
      ScalapTop.all
    }
  }

  def add(url: String, title:String, description:String):Long = {
    DB localTx  { implicit session =>
      ScalapTop.insert(url, title, description, Timestamp.valueOf(LocalDateTime.now()))
    }
  }

  def delete(id: Int) = {
    DB localTx { implicit session =>
      ScalapTop.delete(id)
    }
  }
}
