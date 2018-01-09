package com.teruuu.scalaping.db

import java.sql.Timestamp

import scalikejdbc.{AutoSession, DBSession, SQL, WrappedResultSet}

case class ScalapTop(id: Int, url: String, title: Option[String], description: Option[String], create_date: Timestamp){

  def toView = {
    "id: %d  url: %s  title: %s  description: %s  create_date: %s".format(id, url, title.getOrElse(""), description.getOrElse(""), create_date)
  }
}

object ScalapTop {

  val selectColumns = (rs: WrappedResultSet) =>
    ScalapTop(
      id = rs.int("id"),
      url = rs.string("url"),
      title = Option(rs.string("title")),
      description = Option(rs.string("description")),
      create_date = rs.timestamp("create_date")
    )

  def all(implicit session: DBSession = AutoSession) =
    SQL("select * from scrape_top order by id").map(selectColumns).list().apply()

  def insert(url: String, title:String, description:String, create_date: Timestamp)
            (implicit session: DBSession = AutoSession): Long =
    SQL(
      s"""
        insert into scrape_top (url, title, description, create_date)
        values ( '${url.replace("'","")}', '${title.replace("'","")}', '${description.replace("'","")}', '${create_date}')
        """).updateAndReturnGeneratedKey.apply()

  def delete(id: Int)(implicit session: DBSession = AutoSession) =
    SQL(
      s"""delete from scrape_top
          where id = ${id}""").
      updateAndReturnGeneratedKey.apply()

}
