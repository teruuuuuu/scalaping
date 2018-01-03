package com.teruuu.scalaping.db.config

import com.typesafe.config.ConfigFactory
import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

/**
  * Created by arimuraterutoshi on 2017/02/11.
  */
trait DbSetting {

  def loadJDBCSettings(): Unit ={

    val config = ConfigFactory.load
    Class.forName(config.getString("db.default.driver"))
    val url = config.getString("db.default.url")
    val user = config.getString("db.default.user")
    val password = config.getString("db.default.password")

    val settings = ConnectionPoolSettings(
      initialSize = 5,
      maxSize = 20,
      connectionTimeoutMillis = 3000L,
      validationQuery = "select 1")

    ConnectionPool.singleton(url, user, password, settings)
  }

  loadJDBCSettings()
}
