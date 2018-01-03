package com.teruuu.scalaping.db

import java.sql.Timestamp

import scalikejdbc.{AutoSession, DBSession, SQL, WrappedResultSet}

case class TopLink(id: Int, top_id: Int, url: String, text: Option[String], add_date: Timestamp)

object TopLink {
  val selectColumns = (rs: WrappedResultSet) =>
    TopLink(
      id = rs.int("id"),
      top_id = rs.int("top_id"),
      url = rs.string("url"),
      text = Option(rs.string("text")),
      add_date = rs.timestamp("add_date")
    )

  def all(implicit session: DBSession = AutoSession) =
    SQL("select * from top_link order by top_id, id").map(selectColumns).list().apply()

  def selectByTopId(top_id: Int)(implicit session: DBSession = AutoSession) =
    SQL(s"select * from top_link where top_id = ${top_id} order by id").map(selectColumns).list().apply()

  def insert(top_id: Int, url: String, text:String, add_date: Timestamp)
            (implicit session: DBSession = AutoSession): Long =
    SQL(
      s"""
        insert into top_link (top_id, url, text, add_date)
        values ( '${top_id}', '${url}', '${text}', '${add_date}')
        """).updateAndReturnGeneratedKey.apply()

  def delete(id: Int)(implicit session: DBSession = AutoSession) =
    SQL(
      s"""delete from top_link
          where id = ${id}""").
      updateAndReturnGeneratedKey.apply()


}
