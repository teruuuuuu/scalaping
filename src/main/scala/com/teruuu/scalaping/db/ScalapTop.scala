package com.teruuu.scalaping.db

import java.sql.Timestamp

import scalikejdbc.{AutoSession, DBSession, delete, insert, ResultName, select, SQL, SQLSyntaxSupport, withSQL, WrappedResultSet}

case class ScalapTop(id: Int, url: String, title: Option[String], description: Option[String], create_date: Timestamp)

object ScalapTop extends SQLSyntaxSupport[ScalapTop] {
  override val tableName = "scrape_top"
  val s = ScalapTop.syntax("s")
  val c = ScalapTop.column

  def apply(rs: WrappedResultSet): ScalapTop =
    ScalapTop(rs.int("id"), rs.string("url"), rs.stringOpt("title"), rs.stringOpt("description"), rs.timestamp("create_date"))

  def apply(c: ResultName[ScalapTop])(rs: WrappedResultSet): ScalapTop =
    ScalapTop(rs.int(c.id), rs.string(c.url), rs.stringOpt(c.title), rs.stringOpt(c.title), rs.timestamp(c.create_date))

  def all(implicit session: DBSession = AutoSession) =
    withSQL {
      select(c.id, c.url, c.title, c.description, c.create_date).
        from(ScalapTop as s).
        orderBy(c.id)
    }.map(ScalapTop(_)).list.apply()

  def insertScalapTop(url: String, title:String, description:String, create_date: Timestamp)
            (implicit session: DBSession = AutoSession): Long =
    withSQL {
      insert.into(ScalapTop).namedValues(c.url -> url, c.title -> title,
        c.description -> description, c.create_date -> create_date)
    }.update.apply()

  def deleteScalapTop(id: Int)(implicit session: DBSession = AutoSession) =
    withSQL {
      delete.from(ScalapTop).where.eq(c.id, id)
    }.update.apply()

}
