package com.teruuu.scalaping.workspace

import java.sql.Timestamp
import java.time.{Instant, LocalDateTime, ZoneId, ZoneOffset}

import com.teruuu.scalaping.db.config.{DbSetting, LogSettings}
import com.teruuu.scalaping.db.ScalapTop
import scalikejdbc.DB

object ScalikeJdbcExample extends DbSetting with LogSettings{


  def main(args: Array[String]): Unit ={

    val scalapTopList = DB readOnly {
      implicit session =>
        ScalapTop.all
    }

//    val insert_id = DB localTx  { implicit session =>
//      ScalapTop.insert("http://japanese.engadget.com/", "engadet", "", Timestamp.valueOf(LocalDateTime.now()))
//    }
//    println("id:" + insert_id)
//
//    DB localTx{ implicit session =>
//      ScalapTop.delete(insert_id.toInt)
//    }
  }

}
