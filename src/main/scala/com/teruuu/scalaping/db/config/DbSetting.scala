package com.teruuu.scalaping.db.config

import com.teruuu.scalaping.config.ApplicationConfig
import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

/**
  * Created by arimuraterutoshi on 2017/02/11.
  */
trait DbSetting {

  def loadJDBCSettings(): Unit ={

    val settings = ConnectionPoolSettings(
      initialSize = 5,
      maxSize = 20,
      connectionTimeoutMillis = 3000L,
      validationQuery = "select 1")

    ConnectionPool.singleton(ApplicationConfig.db_url, ApplicationConfig.db_user, ApplicationConfig.db_password, settings)
  }

  loadJDBCSettings()
}
